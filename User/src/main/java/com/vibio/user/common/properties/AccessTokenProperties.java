/*
 *  AccessTokenProperties
 *  @author: Minhhieuano
 *  @created 9/11/2024 10:24 PM
 * */

package com.vibio.user.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt.key.access-token")
public record AccessTokenProperties(String publicKey, String privateKey, Long expireIn) {}
