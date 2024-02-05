package com.vinsguru.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04BucketAsMapTest extends BaseTest {

	@Test
	public void bucketsAsMap() {

		//if key does not exist, it ignores
		Mono<Void> mget = this.client.getBuckets(StringCodec.INSTANCE)
				.get("user:1:name", "user:2:name", "user:3:name")
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(mget)
				.verifyComplete();

	}

}
