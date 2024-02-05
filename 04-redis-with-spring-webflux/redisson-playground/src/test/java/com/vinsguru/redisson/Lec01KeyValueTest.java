package com.vinsguru.redisson;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01KeyValueTest extends BaseTest {

	@Test
	public void keyValueAccessTest() {

		//must set up the string codec
		RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("sam");
		Mono<Void> get = bucket.get()
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(set.concatWith(get))
				.verifyComplete();

	}

	@Test
	public void keyValueExpiryTest() {

		//must set up the string codec
		RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("sam", Duration.ofSeconds(10));
		Mono<Void> get = bucket.get()
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(set.concatWith(get))
				.verifyComplete();

	}

	@Test
	public void keyValueExtendExpiryTest() {

		//must set up the string codec
		RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("sam", Duration.ofSeconds(10));
		Mono<Void> get = bucket.get()
				.doOnNext(System.out::println)
				.then();
		set.concatWith(get).subscribe();

		//extend
		sleep(5000);

		Mono<Boolean> expire = bucket.expire(Duration.ofSeconds(60));

		StepVerifier.create(expire)
				.expectNext(true)
				.verifyComplete();

		//access expiration time
		Mono<Void> ttl = bucket.remainTimeToLive()
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(ttl)
				.verifyComplete();

	}

}
