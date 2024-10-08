/*
 *  VideoController
 *  @author: Minhhieuano
 *  @created 10/7/2024 5:53 PM
 * */


package com.vibio.video.controller;

import com.vibio.video.dto.common.AuthenticatedUser;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/feeds")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getFeeds(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(videoService.getFeeds(user.getId(), page, limit));
    }
}
