/*
 *  ServiceUrl
 *  @author: Minhhieuano
 *  @created 9/26/2024 10:38 PM
 * */

package com.vibio.channel.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services-url")
public record ServiceUrl(String user, String payment, String video) {
}
