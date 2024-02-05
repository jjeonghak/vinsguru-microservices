package com.vinsguru.redisson;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.redisson.api.RDequeReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RQueueReactive;
import org.redisson.client.codec.LongCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec09ListQueueStackTest extends BaseTest {

	@Test
	public void listTest() {

		RListReactive<Long> list = this.client.getList("number-input", LongCodec.INSTANCE);

		List<Long> longList = LongStream.rangeClosed(1, 10)
				.boxed()
				.toList();

		StepVerifier.create(list.addAll(longList).then())
				.verifyComplete();

		StepVerifier.create(list.size())
				.expectNext(10)
				.verifyComplete();

	}

	@Test
	public void queueTest() {

		RQueueReactive<Long> queue = this.client.getQueue("number-input", LongCodec.INSTANCE);

		Mono<Void> lpop = queue.poll()
				.repeat(3)
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(lpop)
				.verifyComplete();

	}

	@Test
	public void stackTest() {

		RDequeReactive<Long> deque = this.client.getDeque("number-input", LongCodec.INSTANCE);
		Mono<Void> rpop = deque.pollLast()
				.repeat(3)
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(rpop)
				.verifyComplete();

	}


}
