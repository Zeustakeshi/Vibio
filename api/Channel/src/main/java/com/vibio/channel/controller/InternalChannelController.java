/*
 *  InternalChannelController
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:24 PM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.request.FindChannelsByIdsRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.InternalChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalChannelController {

    private final InternalChannelService channelService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getChannelByAccountId(@RequestParam("accountId") String accountId) {
        return ApiResponse.success(channelService.getChannelByAccountId(accountId));
    }

    @GetMapping("details")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getChannelDetailInfo(@RequestParam("accountId") String accountId) {
        return ApiResponse.success(channelService.getChannelDetailInfo(accountId));
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> validateChannel(@RequestParam("channelId") String channelId) {
        return ApiResponse.success(channelService.validateChannel(channelId));
    }

    @PostMapping("/infos")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> findChannelByIds(@RequestBody @Valid FindChannelsByIdsRequest request) {
        return ApiResponse.success(channelService.getChannelByIds(request.getIds()));
    }
}
