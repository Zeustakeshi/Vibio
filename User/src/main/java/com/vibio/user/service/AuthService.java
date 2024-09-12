/*
 *  AuthService
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:55 PM
 * */

package com.vibio.user.service;

import com.vibio.user.dto.request.*;
import com.vibio.user.dto.response.OtpResponse;
import com.vibio.user.dto.response.TokenResponse;

public interface AuthService {
	OtpResponse createAccountWithUsernameAndPassword(CreateAccountRequest request);

	Object login(LoginRequest request);

	OtpResponse resendCreateAccountOtp(ResendOtpRequest request);

	OtpResponse resendMfaOtp(ResendOtpRequest request);

	TokenResponse verifyMfaOtp(VerifyOtpRequest request);

	TokenResponse verifyCreateAccountOtp(VerifyOtpRequest request);

	TokenResponse refreshToken(RefreshTokenRequest request);
}
