/*
 *  UserClient
 *  @author: Minhhieuano
 *  @created 10/6/2024 10:05 AM
 * */

package com.vibio.video.client;

import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${services-url.user}")
public interface UserClient {
	@GetMapping("internal/account/{accountId}")
	ApiResponse<UserResponse> getUserByAccountId(@PathVariable("accountId") String accountId);
}
