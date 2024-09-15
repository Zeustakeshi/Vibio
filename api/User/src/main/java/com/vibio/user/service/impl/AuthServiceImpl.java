/*
 *  AuthServiceImpl
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:57 PM
 * */

package com.vibio.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibio.user.common.enums.Role;
import com.vibio.user.domain.AccountConfirmation;
import com.vibio.user.domain.AccountMFA;
import com.vibio.user.domain.OTP;
import com.vibio.user.dto.request.CreateAccountRequest;
import com.vibio.user.dto.request.LoginRequest;
import com.vibio.user.dto.request.ResendOtpRequest;
import com.vibio.user.dto.request.VerifyOtpRequest;
import com.vibio.user.dto.response.OtpResponse;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.exception.BadRequestException;
import com.vibio.user.exception.ConflictException;
import com.vibio.user.exception.NotfoundException;
import com.vibio.user.mapper.AccountMapper;
import com.vibio.user.model.Account;
import com.vibio.user.model.Profile;
import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.AuthService;
import com.vibio.user.service.OtpService;
import com.vibio.user.service.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final Jedis jedis;
    private final OtpService otpService;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public OtpResponse createAccountWithUsernameAndPassword(CreateAccountRequest request) {
        // Check if the email already exists in the database
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already existed!");
        }

        // Create an OTP for account confirmation
        OTP otp = otpService.createAccountConfirmationOTP();

        // Create an AccountConfirmation object from the CreateAccountRequest (encode password)
        AccountConfirmation confirmation = accountMapper.createAccountRequestToAccountConfirmation(request);
        confirmation.setPassword(passwordEncoder.encode(confirmation.getPassword()));
        confirmation.setOtpCode(otp.getCode());

        // Store the AccountConfirmation object in Redis with a specified expiration time
        jedis.setex(
                confirmation.getCode(), TimeUnit.MINUTES.toSeconds(5), objectMapper.writeValueAsString(confirmation));

        // send otp to user via email
        otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp);

        return OtpResponse.builder().code(confirmation.getCode()).build();
    }

    @Override
    public Object login(LoginRequest request) {

        String incorrectEmailOrPasswordMessage = "Incorrect email name or password";

        // Get account information form database
        Account account = accountRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(incorrectEmailOrPasswordMessage));

        // Check account password
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new BadRequestException(incorrectEmailOrPasswordMessage);
        }

        // Check account Multi-factor Authentication (MFA)
        if (account.getMfa()) return multiFactorAuthenticationLogin(account);
        return basicAuthenticationLogin(account);
    }

    @SneakyThrows
    @Override
    public OtpResponse resendCreateAccountOtp(ResendOtpRequest request) {

        // Retrieve the AccountConfirmation object from Redis using the provided code
        String confirmationJson = Optional.ofNullable(jedis.get(request.getCode()))
                .orElseThrow(() -> new BadRequestException("Invalid OTP code"));
        AccountConfirmation confirmation = objectMapper.readValue(confirmationJson, AccountConfirmation.class);
        // Generate a new OTP
        OTP otp = otpService.createAccountConfirmationOTP();

        // Delete the old OTP from Redis
        jedis.del(confirmation.getOtpCode());

        // Update the AccountConfirmation object with the new OTP
        confirmation.setOtpCode(otp.getCode());

        // Save account confirmation to cache
        jedis.setex(
                confirmation.getCode(), TimeUnit.MINUTES.toSeconds(30), objectMapper.writeValueAsString(confirmation));

        // Resend OTP
        otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp);

        return OtpResponse.builder().code(confirmation.getCode()).build();
    }

    @SneakyThrows
    @Override
    public TokenResponse verifyCreateAccountOtp(VerifyOtpRequest request) {

        final String DEFAULT_AVATAR_URL = "https://th.bing.com/th/id/R.69b6c7c1419fedc585d4aac2958c5ae4?rik=Ti4lNMU9Co54jg&pid=ImgRaw&r=0";

        // Retrieve the AccountConfirmation object from Redis using the provided code
        String confirmationJson = Optional.ofNullable(jedis.get(request.getCode()))
                .orElseThrow(() -> new BadRequestException("Invalid OTP code"));
        AccountConfirmation confirmation = objectMapper.readValue(confirmationJson, AccountConfirmation.class);

        verifyOtp(confirmation.getOtpCode(), request);

        jedis.del(confirmation.getCode());
        jedis.del(confirmation.getOtpCode());

        Account account = accountMapper.accountConfirmationToAccount(confirmation);
        account.setRoles(List.of(Role.USER));
        account.setProfile(Profile.builder().build());
        account.setAvatar(DEFAULT_AVATAR_URL);

        return tokenService.generateTokenPair(accountRepository.save(account));
    }

    @SneakyThrows
    @Override
    public OtpResponse resendMfaOtp(ResendOtpRequest request) {
        // Retrieve the MfaConfirmation object from Redis using the provided code
        String confirmationJson = Optional.ofNullable(jedis.get(request.getCode()))
                .orElseThrow(() -> new BadRequestException("Invalid OTP code"));

        AccountMFA confirmation = objectMapper.readValue(confirmationJson, AccountMFA.class);

        // Generate a new OTP
        OTP otp = otpService.createMultiFactorAuthenticationOTP();

        // Delete the old OTP from Redis
        jedis.del(confirmation.getOtpCode());

        // Update the MfaConfirmation object with the new OTP
        confirmation.setOtpCode(otp.getCode());

        // Save MfaConfirmation to cache
        jedis.setex(
                confirmation.getCode(), TimeUnit.MINUTES.toSeconds(20), objectMapper.writeValueAsString(confirmation));

        // Resend OTP
        otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp);

        return OtpResponse.builder().code(confirmation.getCode()).build();
    }

    @SneakyThrows
    @Override
    @Transactional
    public TokenResponse verifyMfaOtp(VerifyOtpRequest request) {
        // Retrieve the accountMfaJson object from Redis using the provided code
        String accountMfaJson = Optional.ofNullable(jedis.get(request.getCode()))
                .orElseThrow(() -> new BadRequestException("Invalid OTP code"));
        AccountMFA accountMFA = objectMapper.readValue(accountMfaJson, AccountMFA.class);

        verifyOtp(accountMFA.getOtpCode(), request);

        jedis.del(accountMFA.getCode());
        jedis.del(accountMFA.getOtpCode());

        Account account = accountRepository
                .findByEmail(accountMFA.getEmail())
                .orElseThrow(() -> new NotfoundException("Account not found"));

        return tokenService.generateTokenPair(account);
    }

    private void verifyOtp(String otpCode, VerifyOtpRequest request) throws JsonProcessingException {
        String otpJson = Optional.ofNullable(jedis.get(otpCode))
                .orElseThrow(() -> new BadRequestException("Your OTP has been expired!"));
        OTP otp = objectMapper.readValue(otpJson, OTP.class);

        if (!passwordEncoder.matches(request.getOtp(), otp.getValue())) {
            throw new BadRequestException("Invalid OTP code");
        }
    }


    @SneakyThrows
    private OtpResponse multiFactorAuthenticationLogin(Account account) {
        // Generate an OTP for Multi-Factor Authentication (MFA)
        OTP otp = otpService.createMultiFactorAuthenticationOTP();

        // create account accountMFAConfirmation object from account
        AccountMFA confirmation = accountMapper.accountToAccountMFAConfirmation(account);
        confirmation.setEmail(account.getEmail());
        confirmation.setOtpCode(otp.getCode());

        jedis.setex(
                confirmation.getCode(), TimeUnit.MINUTES.toSeconds(5), objectMapper.writeValueAsString(confirmation));

        // send otp to user via email
        otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp);

        return OtpResponse.builder().code(confirmation.getCode()).build();
    }

    private TokenResponse basicAuthenticationLogin(Account account) {
        return tokenService.generateTokenPair(account);
    }
}
