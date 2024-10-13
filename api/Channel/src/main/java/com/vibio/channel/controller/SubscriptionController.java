/*
 *  SubscriptionController
 *  @author: Minhhieuano
 *  @created 10/10/2024 10:58 AM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.common.AuthenticatedUser;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscription")
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionService subscriptionService;

	@PostMapping("{channelId}/subscribe")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> subscribeChannel(
			@PathVariable("channelId") String channelId, @AuthenticationPrincipal AuthenticatedUser user) {
		return ApiResponse.success(subscriptionService.subscribeChannel(channelId, user.getId()));
	}

	@PostMapping("{channelId}/unSubscribe")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> unSubscribeChannel(
			@PathVariable("channelId") String channelId, @AuthenticationPrincipal AuthenticatedUser user) {
		return ApiResponse.success(subscriptionService.unSubscribeChannel(channelId, user.getId()));
	}
}
