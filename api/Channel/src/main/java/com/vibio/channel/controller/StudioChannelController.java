/*
 *  StudioChannelController
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:46 PM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.common.AuthenticatedUser;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.StudioChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studio")
@RequiredArgsConstructor
public class StudioChannelController {
	private final StudioChannelService channelService;

	@GetMapping("/info")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getChannelInfo(@AuthenticationPrincipal AuthenticatedUser user) {
		return ApiResponse.success(channelService.getChannelInfo(user.getId()));
	}
}
