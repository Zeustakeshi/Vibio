/*
 *  TokenController
 *  @author: Minhhieuano
 *  @created 9/14/2024 12:49 AM
 * */


package com.vibio.user.controller;

import com.vibio.user.dto.request.IntrospectTokenRequest;
import com.vibio.user.dto.request.RefreshTokenRequest;
import com.vibio.user.dto.response.ApiResponse;
import com.vibio.user.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ApiResponse.success(tokenService.refreshToken(request));
    }

    @PostMapping("/introspect")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> introspectToken(@RequestBody @Valid IntrospectTokenRequest request) {
        return ApiResponse.success(tokenService.introspectToken(request));
    }

}
