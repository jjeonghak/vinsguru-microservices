package com.vinsguru.redisperformance.config;

import java.util.Objects;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	private RedissonClient redissonClient;

	@Bean
	public RedissonClient getClient() {
		if (Objects.isNull(this.redissonClient)) {
			Config config = new Config();
			config.useSingleServer()
					.setAddress("redis://127.0.0.1:6379");
			this.redissonClient = Redisson.create(config);
		}
		return this.redissonClient;
	}

	@Bean
	public RedissonReactiveClient getReactiveClient() {
		return getClient().reactive();
	}

}
