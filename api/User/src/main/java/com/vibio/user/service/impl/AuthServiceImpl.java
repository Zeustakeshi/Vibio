/*
 *  AuthServiceImpl
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:57 PM
 * */

package com.vibio.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibio.user.common.enums.OtpType;
import com.vibio.user.common.enums.Role;
import com.vibio.user.domain.AccountConfirmation;
import com.vibio.user.domain.AccountMFA;
import com.vibio.user.domain.AccountOtpInformation;
import com.vibio.user.domain.OTP;
import com.vibio.user.dto.request.CreateAccountRequest;
import com.vibio.user.dto.request.LoginRequest;
import com.vibio.user.dto.request.ResendOtpRequest;
import com.vibio.user.dto.request.VerifyOtpRequest;
import com.vibio.user.dto.response.OtpResponse;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.event.producer.ChannelProducer;
import com.vibio.user.exception.BadRequestException;
import com.vibio.user.exception.ConflictException;
import com.vibio.user.exception.ForbiddenException;
import com.vibio.user.exception.NotfoundException;
import com.vibio.user.mapper.AccountMapper;
import com.vibio.user.model.Account;
import com.vibio.user.model.Profile;
import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.AuthService;
import com.vibio.user.service.OtpService;
import com.vibio.user.service.TokenService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private static final Integer MAXIMUM_NUMBER_OF_VALIDATE_OTP_REQUEST = 3;
	private static final Integer CONFIRMATION_CODE_EXPIRED_TIME = 5; // 5 minutes
	private final AccountRepository accountRepository;
	private final Jedis jedis;
	private final OtpService otpService;
	private final AccountMapper accountMapper;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	private final ObjectMapper objectMapper;
	private final ChannelProducer channelProducer;

	@Value("${default.account.avatar}")
	private String accountDefaultAvatar;

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
				confirmation.getCode(),
				TimeUnit.MINUTES.toSeconds(CONFIRMATION_CODE_EXPIRED_TIME),
				objectMapper.writeValueAsString(confirmation));

		// send otp to user via email
		otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp, OtpType.ACCOUNT_CREATION);

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
		confirmation.setValidateCount(0);

		// Save account confirmation to cache
		jedis.setex(
				confirmation.getCode(),
				jedis.ttl(confirmation.getCode()),
				objectMapper.writeValueAsString(confirmation));

		// Resend OTP
		otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp, OtpType.ACCOUNT_CREATION);

		return OtpResponse.builder().code(confirmation.getCode()).build();
	}

	@SneakyThrows
	@Override
	public TokenResponse verifyCreateAccountOtp(VerifyOtpRequest request) {

		// Verify OTP and retrieve AccountConfirmation object
		AccountConfirmation confirmation = verifyOtpAndRetrieveData(request, AccountConfirmation.class);

		// If OTP verification succeeds, proceed with account creation
		Account account = accountMapper.accountConfirmationToAccount(confirmation);
		account.setRoles(List.of(Role.USER)); // Assign default user role
		account.setProfile(Profile.builder().build()); // Create an empty profile
		account.setAvatar(accountDefaultAvatar); // Set default avatar

		// Clean up the OTP and confirmation data from Redis
		jedis.del(confirmation.getCode());
		jedis.del(confirmation.getOtpCode());

		// persist account to database
		Account newAccount = accountRepository.save(account);

		// create channel
		channelProducer.createNewChannel(accountMapper.accountToNewChannelEvent(newAccount));

		// Save the new account and generate a token pair for the account
		return tokenService.generateTokenPair(newAccount);
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
				confirmation.getCode(),
				jedis.ttl(confirmation.getCode()),
				objectMapper.writeValueAsString(confirmation));

		// Resend OTP
		otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp, OtpType.MFA);

		return OtpResponse.builder().code(confirmation.getCode()).build();
	}

	@SneakyThrows
	@Override
	@Transactional
	public TokenResponse verifyMfaOtp(VerifyOtpRequest request) {

		// Verify OTP and retrieve AccountMFA object
		AccountMFA accountMFA = verifyOtpAndRetrieveData(request, AccountMFA.class);

		// Find the associated account
		Account account = accountRepository
				.findByEmail(accountMFA.getEmail())
				.orElseThrow(() -> new NotfoundException("Account not found"));

		// Clean up the OTP and MFA data from Redis
		jedis.del(accountMFA.getCode());
		jedis.del(accountMFA.getOtpCode());

		// Generate and return a token pair for the account
		return tokenService.generateTokenPair(account);
	}

	private boolean verifyOtp(String otpCode, VerifyOtpRequest request) throws JsonProcessingException {
		Optional<String> otpJsonOptional = Optional.ofNullable(jedis.get(otpCode));
		if (otpJsonOptional.isEmpty()) return false;
		OTP otp = objectMapper.readValue(otpJsonOptional.get(), OTP.class);
		return passwordEncoder.matches(request.getOtp(), otp.getValue());
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
		otpService.sendOtp(confirmation.getCode(), confirmation.getEmail(), otp, OtpType.MFA);

		return OtpResponse.builder().code(confirmation.getCode()).build();
	}

	private TokenResponse basicAuthenticationLogin(Account account) {
		return tokenService.generateTokenPair(account);
	}

	@SneakyThrows
	private <T extends AccountOtpInformation> T verifyOtpAndRetrieveData(VerifyOtpRequest request, Class<T> clazz) {
		// Retrieve the object from Redis using the provided code
		String otpDataJson = Optional.ofNullable(jedis.get(request.getCode()))
				.orElseThrow(() -> new BadRequestException("Invalid or expired OTP code. Please request a new OTP."));

		// Deserialize the JSON string to the desired class (AccountConfirmation, AccountMFA, etc.)
		T otpData = objectMapper.readValue(otpDataJson, clazz);

		// Check OTP validity
		String storedOtpCode = otpData.getOtpCode();

		if (!verifyOtp(storedOtpCode, request)) {
			int validateCount = otpData.getValidateCount();

			// Check if the maximum number of OTP validation attempts has been exceeded
			if (validateCount > MAXIMUM_NUMBER_OF_VALIDATE_OTP_REQUEST) {
				jedis.del(storedOtpCode);
				throw new ForbiddenException("OTP validation limit exceeded. Please request a new OTP.");
			}

			// Increment validation count and update Redis
			otpData.setValidateCount(validateCount + 1);

			jedis.setex(
					request.getCode(),
					jedis.ttl(request.getCode()), // Preserve the current TTL
					objectMapper.writeValueAsString(otpData) // Save the updated object
					);

			throw new BadRequestException("Invalid OTP. Please try again.");
		}

		return otpData;
	}
}
