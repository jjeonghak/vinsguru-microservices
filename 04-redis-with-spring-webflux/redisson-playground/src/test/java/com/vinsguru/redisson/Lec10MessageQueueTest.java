package com.vinsguru.redisson;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingDequeReactive;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec10MessageQueueTest extends BaseTest {

	private RBlockingDequeReactive<String> msgQueue;

	@BeforeAll
	public void setupQueue() {
		this.msgQueue = this.client.getBlockingDeque("message-queue", StringCodec.INSTANCE);
	}

	@Test
	public void consumer1() {

		this.msgQueue.takeElements()
				.doOnNext(msg -> System.out.println("Consumer1: take msg --> " + msg))
				.doOnError(System.out::println)
				.subscribe();

		sleep(600000);

	}

	@Test
	public void consumer2() {

		this.msgQueue.takeElements()
				.doOnNext(msg -> System.out.println("Consumer2: take msg --> " + msg))
				.doOnError(System.out::println)
				.subscribe();

		sleep(600000);

	}

	@Test
	public void producer() {

		Mono<Void> produce = Flux.range(1, 100)
				.delayElements(Duration.ofMillis(500))
				.map(i -> "msg-" + i)
				.doOnNext(msg -> System.out.println("Producer: produce msg [" + msg + "]"))
				.flatMap(this.msgQueue::add)
				.then();

		StepVerifier.create(produce)
				.verifyComplete();

	}

}
