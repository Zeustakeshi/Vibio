/*
 *  ApplicationConfig
 *  @author: Minhhieuano
 *  @created 10/24/2024 2:44 PM
 * */


package com.vibio.search.config;

import com.vibio.search.commom.properties.ServiceUrl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration
@EnableConfigurationProperties({ServiceUrl.class})
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class ApplicationConfig {
}
