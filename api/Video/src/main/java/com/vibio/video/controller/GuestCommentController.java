/*
 *  GuestCommentController
 *  @author: Minhhieuano
 *  @created 10/7/2024 4:55 PM
 * */

package com.vibio.video.controller;

import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest/{videoId}/comments")
@RequiredArgsConstructor
public class GuestCommentController {
	private final CommentService commentService;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getAllComment(
			@PathVariable("videoId") String videoId,
			@RequestParam(value = "parentId", required = false) String parentId,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {
		return ApiResponse.success(commentService.getAllGuestComment(videoId, parentId, page, limit));
	}
}
