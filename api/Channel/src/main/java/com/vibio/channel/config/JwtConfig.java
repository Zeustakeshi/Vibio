/*
 *  JwtConfifg
 *  @author: Minhhieuano
 *  @created 9/26/2024 10:25 PM
 * */


package com.vibio.channel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

    @Value("${services-url.user}")
    private String userServiceUrl;

    @Bean
    JwtDecoder jwtDecoder() {
        String jwkSetUrl = userServiceUrl + "/token/.well-known/jwks.json";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUrl).build();
    }
}
