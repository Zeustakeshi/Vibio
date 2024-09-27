/*
 *  OtpService
 *  @author: Minhhieuano
 *  @created 9/8/2024 11:37 PM
 * */

package com.vibio.user.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibio.user.common.enums.OTPType;
import com.vibio.user.domain.OTP;
import com.vibio.user.event.eventModel.SendOtpEvent;
import com.vibio.user.event.producer.NotificationProducer;
import com.vibio.user.exception.TooManyRequestsException;
import com.vibio.user.service.OtpService;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

	private static final Integer MAXIMUM_NUMBER_OF_SEND_OTP_REQUEST = 3;
	private static final Integer MAXIMUM_TIME_LIMIT_SEND_OTP_REQUEST = 1; // 1 minutes

	private final PasswordEncoder passwordEncoder;
	private final Jedis jedis;
	private final ObjectMapper objectMapper;
	private final NotificationProducer notificationProducer;

	@Override
	public OTP createAccountConfirmationOTP() {
		// Generate an OTP for account creation
		OTP otp = OTP.builder()
				.value(generateOtp(6))
				.type(OTPType.CREATE_ACCOUNT)
				.expiresIn(TimeUnit.MINUTES.toSeconds(3))
				.build();
		// Save hashed OTP to cache
		OTP hashOtp = hashOtp(otp);
		jedis.setex(otp.getCode(), otp.getExpiresIn(), toOtpString(hashOtp));

		// Return plain OTP
		return otp;
	}

	@Override
	public void clearOtp(String otpCode) {
		jedis.del(otpCode);
	}

	@SneakyThrows
	@Override
	public void sendOtp(String key, String email, OTP plainOtp) {
		String otpLimitKey = createOtpLimitKey(key, plainOtp.getType());

		Integer sendCount = 1; // default send count

		Optional<String> sendCountJSon = Optional.ofNullable(jedis.get(otpLimitKey));

		if (sendCountJSon.isEmpty()) {
			// save new send otp count to cache
			jedis.setex(
					otpLimitKey,
					TimeUnit.MINUTES.toSeconds(MAXIMUM_TIME_LIMIT_SEND_OTP_REQUEST),
					objectMapper.writeValueAsString(sendCount));
		} else {
			// check valid limit send request
			sendCount = objectMapper.readValue(sendCountJSon.get(), Integer.class);

			if (sendCount > MAXIMUM_NUMBER_OF_SEND_OTP_REQUEST) {
				throw new TooManyRequestsException(
						"You have reached the limit for OTP resend attempts. Please try again later.");
			}
			// update send count to cache
			jedis.setex(
					otpLimitKey,
					TimeUnit.MINUTES.toSeconds(MAXIMUM_TIME_LIMIT_SEND_OTP_REQUEST),
					objectMapper.writeValueAsString(sendCount + 1));
		}

		// Send OTP to user via email
		notificationProducer.sendOtp(
				SendOtpEvent.builder().email(email).otpCode(plainOtp.getValue()).build());
	}

	@Override
	public OTP createMultiFactorAuthenticationOTP() {

		// Generate an OTP for account Multi-Factor Authentication (MFA)
		OTP otp = OTP.builder()
				.value(generateOtp(6))
				.type(OTPType.MFA)
				.expiresIn(TimeUnit.MINUTES.toSeconds(3))
				.build();

		// Save hashed OTP to cache
		OTP hashOtp = hashOtp(otp);
		jedis.setex(otp.getCode(), otp.getExpiresIn(), toOtpString(hashOtp));

		// Return plain OTP
		return otp;
	}

	@Override
	public OTP hashOtp(OTP otp) {
		OTP otpClone = otp.clone();
		otpClone.setValue(passwordEncoder.encode(otp.getValue()));
		return otpClone;
	}

	@Override
	@SneakyThrows
	public String toOtpString(OTP otp) {
		return objectMapper.writeValueAsString(otp);
	}

	private String createOtpLimitKey(String key, OTPType otpType) {
		return key + otpType.toString();
	}

	private String generateOtp(int length) {
		return NanoIdUtils.randomNanoId(
				new Random(), new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}, length);
	}
}
