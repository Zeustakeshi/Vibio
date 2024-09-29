/*
 *  CloudinaryConfig
 *  @author: Minhhieuano
 *  @created 9/29/2024 10:57 PM
 * */

package com.vibio.video.config;

import com.cloudinary.Cloudinary;
import com.vibio.video.common.properties.CloudinaryProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {
	private final CloudinaryProperties cloudinaryProperties;

	@Bean
	public Cloudinary cloudinary() {
		Map<String, String> configs = new HashMap<>();
		configs.put("cloud_name", cloudinaryProperties.cloudName());
		configs.put("api_key", cloudinaryProperties.apiKey());
		configs.put("api_secret", cloudinaryProperties.apiSecret());
		return new Cloudinary(configs);
	}
}
