/*
 *  ApplicationConfig
 *  @author: Minhhieuano
 *  @created 10/24/2024 2:44 PM
 * */


package com.vibio.payment.config;

import com.vibio.payment.common.properties.MomoProperties;
import com.vibio.payment.common.properties.ServiceUrl;
import com.vibio.payment.common.properties.UtilProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ServiceUrl.class, MomoProperties.class, UtilProperties.class})
public class ApplicationConfig {
}
