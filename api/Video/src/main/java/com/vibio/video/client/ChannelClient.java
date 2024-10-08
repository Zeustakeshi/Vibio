/*
 *  ChannelClient
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:40 PM
 * */

package com.vibio.video.client;

import com.vibio.video.dto.request.FindChannelByIdsRequest;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.dto.response.ChannelDetailResponse;
import com.vibio.video.dto.response.ChannelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "channel-service", url = "${services-url.channel}")
public interface ChannelClient {
    @GetMapping("/internal/validate")
    ApiResponse<Boolean> validateChannel(@RequestParam("channelId") String channelId);

    @GetMapping("/internal/info/detail")
    ApiResponse<ChannelDetailResponse> getChannelInfoByAccountId(@RequestParam("accountId") String accountId);

    @PostMapping("/internal/infos")
    ApiResponse<List<ChannelResponse>> findChannelByIdInIds(
            @RequestBody FindChannelByIdsRequest request
    );
}
