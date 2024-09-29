/*
 *  InternalChannelController
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:24 PM
 * */


package com.vibio.channel.controller;

import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.InternalChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalChannelController {

    private final InternalChannelService channelService;

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> validateChannel(
            @RequestParam("channelId") String channelId
    ) {
        return ApiResponse.success(channelService.validateChannel(channelId));
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getChannelInfo(@RequestParam("accountId") String accountId) {
        return ApiResponse.success(channelService.getChannelInfo(accountId));
    }

}

