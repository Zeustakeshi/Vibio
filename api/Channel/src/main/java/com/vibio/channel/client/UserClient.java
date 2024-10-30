/*
 *  UserClient
 *  @author: Minhhieuano
 *  @created 10/25/2024 10:11 PM
 * */

package com.vibio.channel.client;

import com.vibio.channel.dto.request.FindAccountsByIdsRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service", url = "${services-url.user}")
public interface UserClient {
    @GetMapping("/internal/account/{accountId}")
    ApiResponse<UserResponse> getUserByAccountId(@PathVariable("accountId") String accountId);

    @PostMapping("/internal/account")
    ApiResponse<List<UserResponse>> getUsersByIds(@RequestBody FindAccountsByIdsRequest request);
}
