/*
 *  C
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:50 AM
 * */

package com.vibio.payment.client;

import com.vibio.payment.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "channel-service", url = "${services-url.channel}")
public interface ChannelClient {

    @GetMapping("/internal/members")
    ApiResponse<Boolean> isChannelMember(@RequestParam("channelId") String channelId, @RequestParam("accountId") String accountId);
}
