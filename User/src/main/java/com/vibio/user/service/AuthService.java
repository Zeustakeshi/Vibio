/*
 *  AuthService
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:55 PM
 * */

package com.vibio.user.service;

import com.vibio.user.dto.request.CreateAccountRequest;
import com.vibio.user.dto.request.RefreshTokenRequest;
import com.vibio.user.dto.request.ResendOtpRequest;
import com.vibio.user.dto.request.VerifyOtpRequest;
import com.vibio.user.dto.response.TokenResponse;

public interface AuthService {
	String createAccountWithUsernameAndPassword(CreateAccountRequest request);

	String resendOTP(ResendOtpRequest request);

	TokenResponse verifyOTP(VerifyOtpRequest request);

	void login();

	TokenResponse refreshToken(RefreshTokenRequest request);
}
