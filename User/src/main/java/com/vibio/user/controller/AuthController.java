/*
 *  AuthController
 *  @author: Minhhieuano
 *  @created 9/8/2024 8:55 PM
 * */

package com.vibio.user.controller;

import com.vibio.user.dto.request.*;
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

    @PostMapping("/resend-otp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> resendOtp(@RequestBody @Valid ResendOtpRequest request) {
        return ApiResponse.success(authService.resendOTP(request));
    }

    @PostMapping("/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> verifyOtp(@RequestBody @Valid VerifyOtpRequest request) {
        return ApiResponse.success(authService.verifyOTP(request));
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ApiResponse.success(authService.refreshToken(request));
    }
}
