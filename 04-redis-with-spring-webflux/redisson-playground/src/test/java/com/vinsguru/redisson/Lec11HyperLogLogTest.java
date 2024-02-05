package com.vinsguru.redisson;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.redisson.api.RHyperLogLogReactive;
import org.redisson.client.codec.LongCodec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec11HyperLogLogTest extends BaseTest {

	@Test
	public void count() {

		RHyperLogLogReactive<Long> hyperLogLog = this.client.getHyperLogLog("user:visits", LongCodec.INSTANCE);

		List<Long> list1 = LongStream.rangeClosed(1, 25000)
				.boxed()
				.toList();

		List<Long> list2 = LongStream.rangeClosed(1, 50000)
				.boxed()
				.toList();

		List<Long> list3 = LongStream.rangeClosed(1, 75000)
				.boxed()
				.toList();

		List<Long> list4 = LongStream.rangeClosed(1, 100000)
				.boxed()
				.toList();

		Mono<Void> combine = Flux.just(list1, list2, list3, list3)
				.flatMap(hyperLogLog::addAll)
				.then();

		StepVerifier.create(combine)
				.verifyComplete();

		hyperLogLog.count()
				.doOnNext(System.out::println)
				.subscribe();

	}

}
