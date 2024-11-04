/*
 *  VideoController
 *  @author: Minhhieuano
 *  @created 10/7/2024 5:53 PM
 * */

package com.vibio.video.controller;

import com.vibio.video.dto.common.AuthenticatedUser;
import com.vibio.video.dto.request.ReactionRequest;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.VideoReactionService;
import com.vibio.video.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final VideoReactionService videoReactionService;

    @GetMapping("/feeds")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getFeeds(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ApiResponse.success(videoService.getFeeds(user.getId(), page, limit));
    }

    @GetMapping("{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getVideoById(
            @PathVariable("videoId") String videoId, @AuthenticationPrincipal AuthenticatedUser user) {
        return ApiResponse.success(videoService.getVideoById(videoId, user.getId()));
    }

    // TODO: handle get related video for individual
//    @GetMapping("{videoId}/related")
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse<?> getRelatedVideo(
//            @PathVariable("videoId") String videoId, @AuthenticationPrincipal AuthenticatedUser user) {
//        return ApiResponse.success(videoService.getVideoById(videoId, user.getId()));
//    }

    @PostMapping("{videoId}/reaction")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> reactionVideo(
            @PathVariable("videoId") String videoId,
            @RequestBody @Valid ReactionRequest request,
            @AuthenticationPrincipal AuthenticatedUser user) {
        videoReactionService.reactionVideo(videoId, user.getId(), request.getReactionType());
        return ApiResponse.success(true);
    }

    @PostMapping("{videoId}/un-reaction")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> unReactionVideo(
            @PathVariable("videoId") String videoId,
            @RequestBody @Valid ReactionRequest request,
            @AuthenticationPrincipal AuthenticatedUser user) {
        videoReactionService.unReactionVideo(videoId, user.getId(), request.getReactionType());
        return ApiResponse.success(true);
    }
}
