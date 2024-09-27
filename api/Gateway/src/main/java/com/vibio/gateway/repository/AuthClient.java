/*
 *  AuthClient
 *  @author: Minhhieuano
 *  @created 9/14/2024 12:47 AM
 * */

package com.vibio.gateway.repository;

import com.vibio.gateway.dto.request.IntrospectTokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", url = "${services.user-service}")
public interface AuthClient {
	@PostMapping("/user/token/introspect")
	Boolean introspectToken(IntrospectTokenRequest request);
}
