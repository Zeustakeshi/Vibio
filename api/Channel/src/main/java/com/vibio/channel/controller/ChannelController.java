/*
 *  ChannelController
 *  @author: Minhhieuano
 *  @created 9/19/2024 2:51 PM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.common.AuthenticatedUser;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.ChannelService;
import com.vibio.channel.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;
    private final SubscriptionService subscriptionService;

    @GetMapping("{channelId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getChannelById(
            @PathVariable("channelId") String channelId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(channelService.getChannelById(channelId, user.getId()));
    }

    @PostMapping("{channelId}/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> subscribeChannel(
            @PathVariable("channelId") String channelId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(subscriptionService.subscribeChannel(channelId, user.getId()));
    }

    @PostMapping("{channelId}/unSubscribe")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> unSubscribeChannel(
            @PathVariable("channelId") String channelId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(subscriptionService.unSubscribeChannel(channelId, user.getId()));
    }
}
