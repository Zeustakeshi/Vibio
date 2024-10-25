/*
 *  UtilProperties
 *  @author: Minhhieuano
 *  @created 10/25/2024 1:10 AM
 * */


package com.vibio.payment.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "utils")
public record UtilProperties(Long membershipFee) {
}
