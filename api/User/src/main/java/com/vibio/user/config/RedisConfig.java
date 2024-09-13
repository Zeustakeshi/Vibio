/*
 *  RedisConfig
 *  @author: Minhhieuano
 *  @created 9/8/2024 9:31 PM
 * */

package com.vibio.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

	@Value("${cache.redis_url}")
	private String redisUrl;

	@Bean
	public Jedis jedis() {
		return new Jedis(redisUrl);
	}
}
