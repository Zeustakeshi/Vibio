/*
 *  VideoController
 *  @author: Minhhieuano
 *  @created 9/29/2024 3:57 PM
 * */

package com.vibio.video.controller;

import com.vibio.video.dto.common.AuthenticatedUser;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class VideoController {

	private final VideoService videoService;

	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> uploadVideo(@AuthenticationPrincipal AuthenticatedUser user, MultipartFile video) {
		return ApiResponse.success(videoService.uploadVideo(video, user.getId()));
	}
}
