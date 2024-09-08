/*
 *  OtpService
 *  @author: Minhhieuano
 *  @created 9/8/2024 11:34 PM
 * */

package com.vibio.user.service;

import com.vibio.user.domain.OTP;

public interface OtpService {

	OTP generateCreateAccountOTP();

	OTP hashOtp(OTP plainOtp);

	String toOtpString(OTP otp);
}
