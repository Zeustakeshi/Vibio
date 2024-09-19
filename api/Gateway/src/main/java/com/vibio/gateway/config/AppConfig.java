/*
 *  AppConfig
 *  @author: Minhhieuano
 *  @created 9/14/2024 1:28 AM
 * */


package com.vibio.gateway.config;

import com.vibio.gateway.common.ServiceEndpointProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ServiceEndpointProperties.class})
public class AppConfig {
}
