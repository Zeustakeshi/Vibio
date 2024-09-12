/*
 *  AuthServiceImpl
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:57 PM
 * */

package com.vibio.user.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibio.user.common.enums.Role;
import com.vibio.user.domain.AccountConfirmation;
import com.vibio.user.domain.OTP;
import com.vibio.user.dto.request.*;
import com.vibio.user.dto.response.OtpResponse;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.exception.BadRequestException;
import com.vibio.user.exception.ConflictException;
import com.vibio.user.mapper.AccountMapper;
import com.vibio.user.model.Account;
import com.vibio.user.model.Profile;
import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.AuthService;
import com.vibio.user.service.OtpService;
import com.vibio.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
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
        LocalDateTime now = LocalDateTime.now();
        AccountConfirmation confirmation = accountMapper.createAccountRequestToAccountConfirmation(request);
        confirmation.setPassword(passwordEncoder.encode(confirmation.getPassword()));
        confirmation.setOtpCode(otp.getCode());
        confirmation.setExpireIn(now.plusMinutes(30));

        // Store the AccountConfirmation object in Redis with a specified expiration time
        jedis.setex(confirmation.getCode(), TimeUnit.MINUTES.toSeconds(30), objectMapper.writeValueAsString(confirmation));

        // send otp to user via email
        otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp);

        return OtpResponse.builder()
                .code(confirmation.getCode())
                .build();
    }

    @Override
    public Object login(LoginRequest request) {

        String incorrectEmailOrPasswordMessage = "Incorrect email name or password";

        // Get account information form database
        Account account = accountRepository.findByEmail(request.getEmail())
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
    public String resendOTP(ResendOtpRequest request) {

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
                confirmation.getCode(),
                TimeUnit.MINUTES.toSeconds(30),
                objectMapper.writeValueAsString(confirmation)
        );

        // Resend OTP
        otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp);

        return confirmation.getCode();
    }

    @SneakyThrows
    @Override
    public TokenResponse verifyOTP(VerifyOtpRequest request) {

        // Retrieve the AccountConfirmation object from Redis using the provided code
        String confirmationJson = Optional.ofNullable(jedis.get(request.getCode()))
                .orElseThrow(() -> new BadRequestException("Invalid OTP code"));
        AccountConfirmation confirmation = objectMapper.readValue(confirmationJson, AccountConfirmation.class);

        String otpJson = Optional.ofNullable(jedis.get(confirmation.getOtpCode()))
                .orElseThrow(() -> new BadRequestException("Your OTP has been expired!"));
        OTP otp = objectMapper.readValue(otpJson, OTP.class);

        if (!passwordEncoder.matches(request.getOtp(), otp.getValue())) {
            throw new BadRequestException("Invalid OTP code");
        }

        jedis.del(confirmation.getCode());
        jedis.del(confirmation.getOtpCode());

        Account account = accountMapper.accountConfirmationToAccount(confirmation);
        account.setRoles(List.of(Role.USER));
        account.setProfile(Profile.builder().build());

        return tokenService.generateTokenPair(accountRepository.save(account));
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return tokenService.refreshToken(request.getToken());
    }

    private OtpResponse multiFactorAuthenticationLogin(Account account) {
        // Generate an OTP for Multi-Factor Authentication (MFA)
        OTP otp = otpService.generateMultiFactorAuthenticationOTP();
        String plainOtp = otp.getValue();
        jedis.setex(otp.getCode(), otp.getExpiresIn(), otpService.toOtpString(otpService.hashOtp(otp)));
        return null;
    }

    private TokenResponse basicAuthenticationLogin(Account account) {
        return tokenService.generateTokenPair(account);
    }

}
