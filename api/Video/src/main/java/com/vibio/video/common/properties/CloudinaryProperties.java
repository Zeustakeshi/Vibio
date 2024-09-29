/*
 *  CloudinaryProperties
 *  @author: Minhhieuano
 *  @created 9/29/2024 10:43 PM
 * */

package com.vibio.video.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloudinary")
public record CloudinaryProperties(
		String cloudName, String apiKey, String apiSecret, String dirPrefix, Integer secureUrlExpireIn) {}
