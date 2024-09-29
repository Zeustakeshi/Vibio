/*
 *  AppConfig
 *  @author: Minhhieuano
 *  @created 9/29/2024 3:53 PM
 * */


package com.vibio.video.config;

import com.vibio.video.properties.ServiceUrl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceUrl.class)
public class AppConfig {
}
