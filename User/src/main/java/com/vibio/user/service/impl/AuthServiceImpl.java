/*
 *  AuthServiceImple
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:57 PM
 * */

package com.vibio.user.service.impl;

import com.google.gson.Gson;
import com.vibio.user.common.enums.Role;
import com.vibio.user.domain.AccountConfirmation;
import com.vibio.user.domain.OTP;
import com.vibio.user.dto.request.CreateAccountRequest;
import com.vibio.user.dto.request.ResendOtpRequest;
import com.vibio.user.dto.request.VerifyOtpRequest;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.exception.BadRequestException;
import com.vibio.user.exception.ConflictException;
import com.vibio.user.exception.ForbiddenException;
import com.vibio.user.mapper.AccountMapper;
import com.vibio.user.model.Account;
import com.vibio.user.model.Profile;
import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.AuthService;
import com.vibio.user.service.OtpService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AccountRepository accountRepository;
	private final Jedis jedis;
	private final Gson gson;
	private final OtpService otpService;
	private final AccountMapper accountMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String createAccountWithUsernameAndPassword(CreateAccountRequest request) {
		// Check if the email already exists in the database
		if (accountRepository.existsByEmail(request.getEmail())) {
			throw new ConflictException("Email already existed!");
		}

		// Generate an OTP for account creation
		OTP otp = otpService.generateCreateAccountOTP();
		String plainOtp = otp.getValue();
		jedis.setex(otp.getCode(), otp.getExpiresIn(), otpService.toOtpString(otpService.hashOtp(otp)));

		// Create an AccountConfirmation object from the CreateAccountRequest (encode password)
		AccountConfirmation confirmation = accountMapper.createAccountRequestToAccountConfirmation(request);
		confirmation.setPassword(passwordEncoder.encode(confirmation.getPassword()));
		confirmation.setOtpCode(otp.getCode());

		// Store the AccountConfirmation object in Redis with a specified expiration time
		jedis.setex(confirmation.getCode(), TimeUnit.MINUTES.toSeconds(30), gson.toJson(confirmation));

		System.out.println(plainOtp);

		// TODO: Send OTP to user via email

		return confirmation.getCode();
	}

	@Override
	public String resendOTP(ResendOtpRequest request) {
		final int MAXIMUM_NUMBER_OF_RESEND_REQUEST = 5;

		// Retrieve the AccountConfirmation object from Redis using the provided code
		String confirmationJson = Optional.ofNullable(jedis.get(request.getCode()))
				.orElseThrow(() -> new BadRequestException("Invalid OTP code"));
		AccountConfirmation confirmation = gson.fromJson(confirmationJson, AccountConfirmation.class);

		// Check if the maximum number of resend attempts has been reached
		if (confirmation.getResendCount() >= MAXIMUM_NUMBER_OF_RESEND_REQUEST) {
			throw new ForbiddenException("You have reached the limit for OTP resend attempts. Please try again later.");
		}

		// Generate a new OTP
		OTP otp = otpService.generateCreateAccountOTP();
		String plainOtp = otp.getValue();
		jedis.setex(otp.getCode(), otp.getExpiresIn(), otpService.toOtpString(otpService.hashOtp(otp)));

		// Delete the old OTP from Redis
		jedis.del(confirmation.getOtpCode());

		// Update the AccountConfirmation object with the new OTP and increment the resend count
		confirmation.setOtpCode(otp.getCode());
		confirmation.setResendCount(confirmation.getResendCount() + 1);

		jedis.setex(confirmation.getCode(), TimeUnit.MINUTES.toSeconds(30), gson.toJson(confirmation));

		// TODO: Send OTP to user via email

		System.out.println(plainOtp);

		return confirmation.getCode();
	}

	@Override
	public TokenResponse verifyOTP(VerifyOtpRequest request) {

		// Retrieve the AccountConfirmation object from Redis using the provided code
		String confirmationJson = Optional.ofNullable(jedis.get(request.getCode()))
				.orElseThrow(() -> new BadRequestException("Invalid OTP code"));
		AccountConfirmation confirmation = gson.fromJson(confirmationJson, AccountConfirmation.class);

		String otpJson = Optional.ofNullable(jedis.get(confirmation.getOtpCode()))
				.orElseThrow(() -> new BadRequestException("Your OTP has been expired!"));
		OTP otp = gson.fromJson(otpJson, OTP.class);

		if (!passwordEncoder.matches(request.getOtp(), otp.getValue())) {
			throw new BadRequestException("Invalid OTP code");
		}

		jedis.del(confirmation.getCode());
		jedis.del(confirmation.getOtpCode());

		Account account = accountMapper.accountConfirmationToAccount(confirmation);
		account.setRoles(List.of(Role.USER));
		account.setProfile(Profile.builder().build());

		accountRepository.save(account);

		return TokenResponse.builder().build();
	}

	@Override
	public void login() {}
}
