/*
 *  AppConfig
 *  @author: Minhhieuano
 *  @created 9/26/2024 10:42 PM
 * */

package com.vibio.channel.config;

import com.vibio.channel.common.properties.MembershipProperties;
import com.vibio.channel.common.properties.ServiceUrl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ServiceUrl.class, MembershipProperties.class})
public class AppConfig {}
