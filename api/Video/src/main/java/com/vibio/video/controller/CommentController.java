/*
 *  CommentController
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:59 AM
 * */

package com.vibio.video.controller;

import com.vibio.video.dto.common.AuthenticatedUser;
import com.vibio.video.dto.request.CommentRequest;
import com.vibio.video.dto.request.ReactionRequest;
import com.vibio.video.dto.request.UpdateCommentRequest;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.CommentReactionService;
import com.vibio.video.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{videoId}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final CommentReactionService commentReactionService;

	// create comment;
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<?> createComment(
			@PathVariable("videoId") String videoId,
			@RequestBody @Valid CommentRequest request,
			@AuthenticationPrincipal AuthenticatedUser user) {
		return ApiResponse.success(commentService.crateComment(videoId, user.getId(), request));
	}

	// get all comment by parentId and videoId
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> getAllComment(
			@PathVariable("videoId") String videoId,
			@RequestParam(value = "parentId", required = false) String parentId,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
			@AuthenticationPrincipal AuthenticatedUser user) {
		return ApiResponse.success(commentService.getAllComment(videoId, user.getId(), parentId, page, limit));
	}

	// update comment
	@PatchMapping("{commentId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> updateComment(
			@PathVariable("videoId") String videoId,
			@PathVariable("commentId") String commentId,
			@RequestBody @Valid UpdateCommentRequest request,
			@AuthenticationPrincipal AuthenticatedUser user) {
		return ApiResponse.success(commentService.updateComment(videoId, user.getId(), commentId, request));
	}

	@DeleteMapping("{commentId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> deleteComment(
			@PathVariable("videoId") String videoId,
			@PathVariable("commentId") String commentId,
			@AuthenticationPrincipal AuthenticatedUser user) {
		return ApiResponse.success(commentService.deleteComment(videoId, user.getId(), commentId));
	}

	// reaction comment
	@PostMapping("{commentId}/reaction")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<?> reactionComment(
			@PathVariable("videoId") String videoId,
			@PathVariable("commentId") String commentId,
			@RequestBody @Valid ReactionRequest request,
			@AuthenticationPrincipal AuthenticatedUser user) {
		commentReactionService.reactionComment(commentId, user.getId(), request.getReactionType());
		return ApiResponse.success(true);
	}

	// un reaction comment
	@PostMapping("{commentId}/un-reaction")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<?> unReactionComment(
			@PathVariable("videoId") String videoId,
			@PathVariable("commentId") String commentId,
			@RequestBody @Valid ReactionRequest request,
			@AuthenticationPrincipal AuthenticatedUser user) {
		commentReactionService.unReactionComment(commentId, user.getId(), request.getReactionType());
		return ApiResponse.success(true);
	}
}
