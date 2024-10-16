/*
 *  JwtConfig
 *  @author: Minhhieuano
 *  @created 9/29/2024 3:28 PM
 * */

package com.vibio.notification.config;

import com.vibio.notification.common.properties.ServiceUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
	private final ServiceUrl serviceUrl;

	@Bean
	JwtDecoder jwtDecoder() {
		String jwkSetUrl = serviceUrl.user() + "/token/.well-known/jwks.json";
		return NimbusJwtDecoder.withJwkSetUri(jwkSetUrl).build();
	}
}
