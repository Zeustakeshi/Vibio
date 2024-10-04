/*
 *  VideoController
 *  @author: Minhhieuano
 *  @created 9/29/2024 3:57 PM
 * */

package com.vibio.video.controller;

import com.vibio.video.dto.common.AuthenticatedUser;
import com.vibio.video.dto.request.UpdateVideoRequest;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.StudioVideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class StudioVideoController {

    private final StudioVideoService studioVideoService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAllChannelVideos(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ApiResponse.success(studioVideoService.getChannelVideos(user.getId(), page, limit));
    }

    @GetMapping("{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getVideoDetail(
            @PathVariable("videoId") String videoId, @AuthenticationPrincipal AuthenticatedUser user) {
        return ApiResponse.success(studioVideoService.getVideoDetail(videoId, user.getId()));
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> uploadVideo(@AuthenticationPrincipal AuthenticatedUser user, MultipartFile video) {
        return ApiResponse.success(studioVideoService.uploadVideo(video, user.getId()));
    }

    @PostMapping("/{videoId}/thumbnail")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updateThumbnail(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal AuthenticatedUser user,
            MultipartFile thumbnail) {
        return ApiResponse.success(studioVideoService.updateThumbnail(videoId, user.getId(), thumbnail));
    }

    @PatchMapping("{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updateVideo(
            @PathVariable("videoId") String videoId,
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestBody @Valid UpdateVideoRequest request) {
        return ApiResponse.success(studioVideoService.updateVideo(videoId, user.getId(), request));
    }
}
