package com.vinsguru.redisson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redisson.dto.Student;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec07MapCacheTest extends BaseTest {

	@Test
	public void mapCacheTest() {

		//Redisson map cache extends Redis hash
		//can set up ttl each element in collection(set, zset)
		TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
		RMapCacheReactive<Integer, Student> cache = this.client.getMapCache("users:cache", codec);

		Student user1 = new Student("sam", 10, "atlanta", List.of(1));
		Student user2 = new Student("jake", 30, "miami", List.of(1, 2, 3));

		//set up ttl each element
		Mono<Student> hset1 = cache.put(1, user1, 5, TimeUnit.SECONDS);
		Mono<Student> hset2 = cache.put(2, user2, 10, TimeUnit.SECONDS);

		StepVerifier.create(hset1.concatWith(hset2))
				.verifyComplete();

		Mono<Student> hget1 = cache.get(1).doOnNext(System.out::println);
		Mono<Student> hget2 = cache.get(2).doOnNext(System.out::println);

		//access students
		sleep(3000);
		hget1.subscribe();
		hget2.subscribe();

		//again
		sleep(3000);
		hget1.subscribe();
		hget2.subscribe();

	}

}
