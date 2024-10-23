/*
 *  ChannelClient
 *  @author: Minhhieuano
 *  @created 10/19/2024 11:10 PM
 * */


package com.vibio.user.client;

import com.vibio.user.dto.response.ApiResponse;
import com.vibio.user.dto.response.ChannelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "channel-service", url = "${services-url.channel}")
public interface ChannelClient {
    @GetMapping("/internal")
    ApiResponse<ChannelResponse> getChannelByAccountId(@RequestParam("accountId") String accountId);
}
