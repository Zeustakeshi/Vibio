/*
 *  ApplicationConfig
 *  @author: Minhhieuano
 *  @created 10/16/2024 8:10 AM
 * */

package com.vibio.notification.config;

import com.vibio.notification.common.properties.ServiceUrl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ServiceUrl.class})
public class ApplicationConfig {}
