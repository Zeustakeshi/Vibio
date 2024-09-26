/*
 *  ChannelController
 *  @author: Minhhieuano
 *  @created 9/19/2024 2:51 PM
 * */


package com.vibio.channel.controller;

import com.vibio.channel.dto.common.AuthenticatedUser;
import com.vibio.channel.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelController {

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIdN')")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> hello(@AuthenticationPrincipal AuthenticatedUser user) {
        return ApiResponse.success("Hello " + user.getEmail());
    }
}
