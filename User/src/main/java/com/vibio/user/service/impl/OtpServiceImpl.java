/*
 *  OtpService
 *  @author: Minhhieuano
 *  @created 9/8/2024 11:37 PM
 * */

package com.vibio.user.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.google.gson.Gson;
import com.vibio.user.domain.OTP;
import com.vibio.user.service.OtpService;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
	private final PasswordEncoder passwordEncoder;
	private final Gson gson;

	@Override
	public OTP generateCreateAccountOTP() {
		return OTP.builder()
				.value(generateOTP(5))
				.expiresIn(TimeUnit.MINUTES.toSeconds(5))
				.build();
	}

	@Override
	public OTP hashOtp(OTP otp) {
		otp.setValue(passwordEncoder.encode(otp.getValue()));
		return otp;
	}

	@Override
	public String toOtpString(OTP otp) {
		return gson.toJson(otp);
	}

	private String generateOTP(int length) {
		return NanoIdUtils.randomNanoId(
				new Random(), new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}, length);
	}
}
