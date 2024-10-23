/*
 *  InternalVideoController
 *  @author: Minhhieuano
 *  @created 10/17/2024 6:42 PM
 * */


package com.vibio.video.controller;

import com.vibio.video.dto.request.FindVideosByIdsRequest;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.service.InternalVideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalVideoController {

    private final InternalVideoService internalVideoService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAllVideosByIds(
            @RequestBody @Valid FindVideosByIdsRequest request
    ) {
        return ApiResponse.success(internalVideoService.getAllVideoByIds(request));
    }

    @GetMapping("/introspect")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> introspectVideo(
            @RequestParam("videoId") String videoId
    ) {
        return ApiResponse.success(internalVideoService.introspectVideo(videoId));
    }


}
