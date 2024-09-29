/*
 *  VideoController
 *  @author: Minhhieuano
 *  @created 9/29/2024 3:57 PM
 * */


package com.vibio.video.controller;

import com.vibio.video.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class VideoController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> helloWorld() {
        return ApiResponse.success("Hello world");
    }
}
