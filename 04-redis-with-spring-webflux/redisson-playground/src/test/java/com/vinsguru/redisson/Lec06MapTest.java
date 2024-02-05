package com.vinsguru.redisson;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redisson.dto.Student;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06MapTest extends BaseTest {
	
	@Test
	public void mapTest1() {

		RMapReactive<String, String> hash = this.client.getMap("user:1", StringCodec.INSTANCE);
		Mono<String> hsetName = hash.put("name", "sam");
		Mono<String> hsetAge = hash.put("age", "10");
		Mono<String> hsetCity = hash.put("city", "atlanta");

		StepVerifier.create(hsetName.concatWith(hsetAge).concatWith(hsetCity))
				.verifyComplete();

	}

	@Test
	public void mapTest2() {

		RMapReactive<String, String> hash = this.client.getMap("user:2", StringCodec.INSTANCE);
		Map<String, String> map = Map.of(
				"name", "jake",
				"age", "30",
				"city", "miami"
		);

		StepVerifier.create(hash.putAll(map))
				.verifyComplete();

	}

	@Test
	public void mapTest3() {

		TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
		RMapReactive<Integer, Student> hash = this.client.getMap("users", codec);

		Student user1 = new Student("sam", 10, "atlanta", List.of(1));
		Student user2 = new Student("jake", 30, "miami", List.of(1, 2, 3));

		Mono<Student> hset1 = hash.put(1, user1);
		Mono<Student> hset2 = hash.put(2, user2);

		StepVerifier.create(hset1.concatWith(hset2))
				.verifyComplete();

	}

}
