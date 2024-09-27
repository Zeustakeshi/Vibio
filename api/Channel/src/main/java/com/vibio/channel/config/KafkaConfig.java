/*
 *  KafkaConfig
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:58 PM
 * */

package com.vibio.channel.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class KafkaConfig {

	@Bean
	public JsonMessageConverter jsonMessageConverter() {
		return new ByteArrayJsonMessageConverter();
	}

	@Bean
	NewTopic newChannelTopic() {
		return TopicBuilder.name("new_channel").build();
	}
}
