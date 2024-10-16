/*
 *  ServiceUrl
 *  @author: Minhhieuano
 *  @created 10/16/2024 8:09 AM
 * */

package com.vibio.notification.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services-url")
public record ServiceUrl(String user) {}
