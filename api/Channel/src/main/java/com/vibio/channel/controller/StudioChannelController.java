/*
 *  StudioChannelController
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:46 PM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.common.AuthenticatedUser;
import com.vibio.channel.dto.request.UpdateChannelRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.StudioChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

	@PatchMapping()
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> updateChannelInfo(
			@AuthenticationPrincipal AuthenticatedUser user, @RequestBody @Valid UpdateChannelRequest request) {

		return ApiResponse.success(channelService.updateChannelInfo(user.getId(), request));
	}
}
