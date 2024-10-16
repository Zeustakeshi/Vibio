/*
 *  ThymeleafConfig
 *  @author: Minhhieuano
 *  @created 10/16/2024 9:24 AM
 * */

package com.vibio.notification.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
@RequiredArgsConstructor
public class ThymeleafConfig {
	private final SpringTemplateEngine springTemplateEngine;

	@Bean
	public TemplateEngine thymeleafTemplateEngine() {
		return springTemplateEngine;
	}
}
