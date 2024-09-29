/*
 *  ChannelClient
 *  @author: Minhhieuano
 *  @created 9/29/2024 4:40 PM
 * */

package com.vibio.video.client;

import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.dto.response.ChannelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "channel-service", url = "${services-url.channel}")
public interface ChannelClient {
	@GetMapping("/internal/validate")
	ApiResponse<Boolean> validateChannel(@RequestParam("channelId") String channelId);

	@GetMapping("/internal/info")
	ApiResponse<ChannelResponse> getChannelInfoByAccountId(@RequestParam("accountId") String accountId);
}
