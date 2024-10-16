/*
 *  OtpService
 *  @author: Minhhieuano
 *  @created 9/8/2024 11:34 PM
 * */

package com.vibio.user.service;

import com.vibio.user.common.enums.OtpType;
import com.vibio.user.domain.OTP;

public interface OtpService {

	OTP createAccountConfirmationOTP();

	OTP createMultiFactorAuthenticationOTP();

	void sendOtp(String key, String email, OTP plainOtp, OtpType otpType);

	void clearOtp(String otpCode);

	OTP hashOtp(OTP plainOtp);

	String toOtpString(OTP otp);
}
