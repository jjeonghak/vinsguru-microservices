package com.vinsguru.redisson.config;

import java.util.Objects;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;

public class RedissonConfig {

	private RedissonClient redissonClient;

	public RedissonClient getClient() {
		if (Objects.isNull(this.redissonClient)) {
			Config config = new Config();
			config.useSingleServer()
					.setAddress("redis://127.0.0.1:6379")
					.setUsername("jeonghak")
					.setPassword("jeonghak");
			this.redissonClient = Redisson.create(config);
		}
		return this.redissonClient;
	}

	public RedissonReactiveClient getReactiveClient() {
		return getClient().reactive();
	}

}
