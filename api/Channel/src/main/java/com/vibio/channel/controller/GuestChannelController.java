/*
 *  GuestChannelController
 *  @author: Minhhieuano
 *  @created 10/9/2024 12:27 AM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.repository.PlaylistRepository;
import com.vibio.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestChannelController {
    private final ChannelService channelService;
    private final PlaylistRepository playlistRepository;

    @GetMapping("{channelId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getChannelById(@PathVariable("channelId") String channelId) {
        return ApiResponse.success(channelService.getChannelByIdGuest(channelId));
    }
}
