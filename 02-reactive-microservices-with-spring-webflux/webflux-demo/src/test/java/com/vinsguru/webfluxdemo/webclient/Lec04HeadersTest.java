package com.vinsguru.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04HeadersTest extends BaseTest {

	@Test
	public void headerTest() {

		Mono<Integer> response = webClient
				.get()
				.uri("/calculator/{a}/{b}", 5, 2)
				.headers(h -> h.set("Operation", "*"))
				.retrieve()
				.bodyToMono(Integer.class)
				.doOnNext(System.out::println);

		StepVerifier.create(response)
				.expectNext(10)
				.verifyComplete();

	}

}
