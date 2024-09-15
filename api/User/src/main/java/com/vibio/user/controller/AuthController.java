/*
 *  AuthController
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:55 PM
 * */

package com.vibio.user.controller;

import com.vibio.user.dto.request.CreateAccountRequest;
import com.vibio.user.dto.request.LoginRequest;
import com.vibio.user.dto.request.ResendOtpRequest;
import com.vibio.user.dto.request.VerifyOtpRequest;
import com.vibio.user.dto.response.ApiResponse;
import com.vibio.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> createAccount(@RequestBody @Valid CreateAccountRequest request) {
        return ApiResponse.success(authService.createAccountWithUsernameAndPassword(request));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping("/new-account/resend-otp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> resendCreateAccountOtp(@RequestBody @Valid ResendOtpRequest request) {
        return ApiResponse.success(authService.resendCreateAccountOtp(request));
    }

    @PostMapping("/new-account/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> verifyCreateAccountOtp(@RequestBody @Valid VerifyOtpRequest request) {
        return ApiResponse.success(authService.verifyCreateAccountOtp(request));
    }

    @PostMapping("/mfa/resend-otp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> resendMfaOtp(@RequestBody @Valid ResendOtpRequest request) {
        return ApiResponse.success(authService.resendMfaOtp(request));
    }

    @PostMapping("/mfa/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> verifyMfaOtp(@RequestBody @Valid VerifyOtpRequest request) {
        return ApiResponse.success(authService.verifyMfaOtp(request));
    }


}
