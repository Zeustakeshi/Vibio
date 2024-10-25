/*
 *  MomoProperties
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:38 PM
 * */


package com.vibio.payment.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "payment-gateway.momo")
public record MomoProperties(String partnerCode, String accessKey, String secretKey, String endpoint) {
}
