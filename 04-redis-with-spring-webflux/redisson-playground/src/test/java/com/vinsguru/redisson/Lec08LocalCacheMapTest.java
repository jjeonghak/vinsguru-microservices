package com.vinsguru.redisson;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redisson.config.RedissonConfig;
import com.vinsguru.redisson.dto.Student;

import reactor.core.publisher.Flux;

public class Lec08LocalCacheMapTest extends BaseTest {

	private RLocalCachedMap<Integer, Student> cachedMap;

	@BeforeAll
	private void setupClient() {
		//can not use reactive client
		RedissonConfig config = new RedissonConfig();
		RedissonClient redissonClient = config.getClient();

		//can set up sync and reconnection strategy
		LocalCachedMapOptions<Integer, Student> mapOptions = LocalCachedMapOptions.<Integer, Student>defaults()
				.syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
				.reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE);

		this.cachedMap = redissonClient.getLocalCachedMap(
				"users",
				new TypedJsonJacksonCodec(Integer.class, Student.class),
				mapOptions
		);
	}

	@Test
	public void updateStrategyTest() {

		Student user1 = new Student("sam", 10, "atlanta", List.of(1));
		Student user2 = new Student("jake", 30, "miami", List.of(1, 2, 3));
		this.cachedMap.put(1, user1);
		this.cachedMap.put(2, user2);

		Flux.interval(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println("updateStrategyTest: " + i + " --> " + cachedMap.get(1)))
				.subscribe();

		sleep(60000);

	}

	@Test
	public void supportUpdateStrategyTest() {

		Student user1 = new Student("sam-updated", 15, "atlanta", List.of(1));
		this.cachedMap.put(1, user1);

	}


}
