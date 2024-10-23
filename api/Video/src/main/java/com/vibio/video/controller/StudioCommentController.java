/*
 *  StudioCommentController
 *  @author: Minhhieuano
 *  @created 10/16/2024 10:46 AM
 * */


package com.vibio.video.controller;

import com.vibio.video.dto.common.AuthenticatedUser;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.StudioCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studio/{videoId}/comments")
@RequiredArgsConstructor
public class StudioCommentController {

    private final StudioCommentService commentService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAllComments(
            @PathVariable("videoId") String videoId,
            @RequestParam(value = "parentId", required = false) String parentId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ApiResponse.success(commentService.getComments(videoId, user.getId(), parentId, page, limit));
    }
}
