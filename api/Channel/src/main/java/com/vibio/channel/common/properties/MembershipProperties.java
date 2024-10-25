/*
 *  MembershipProperties
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:06 PM
 * */

package com.vibio.channel.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config.membership")
public record MembershipProperties(Integer expiredIn) {}
