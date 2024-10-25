/*
 *  VideoClient
 *  @author: Minhhieuano
 *  @created 10/17/2024 6:57 PM
 * */

package com.vibio.channel.client;

import com.vibio.channel.dto.request.FindVideosByIdsRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.dto.response.VideoResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "video-service", url = "${services-url.video}")
public interface VideoClient {
	@GetMapping("/internal/introspect")
	ApiResponse<Boolean> introspectVideo(@RequestParam("videoId") String videoId);

	@PostMapping("/internal")
	ApiResponse<List<VideoResponse>> findVideosByIds(@RequestBody @Valid FindVideosByIdsRequest request);
}
