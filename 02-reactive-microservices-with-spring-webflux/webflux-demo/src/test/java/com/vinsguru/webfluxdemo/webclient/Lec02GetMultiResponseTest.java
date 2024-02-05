package com.vinsguru.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;

import com.vinsguru.webfluxdemo.dtos.Response;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec02GetMultiResponseTest extends BaseTest {

	@Test
	public void fluxTest() {

		Flux<Response> response = webClient
				.get()
				.uri("/reactive/math/table/{number}", 5)
				.retrieve()
				.bodyToFlux(Response.class)
				.doOnNext(System.out::println);

		StepVerifier.create(response)
				.expectNextCount(10)
				.verifyComplete();

	}

	@Test
	public void fluxStreamTest() {

		Flux<Response> response = webClient
				.get()
				.uri("/reactive/math/table/stream/{number}", 5)
				.retrieve()
				.bodyToFlux(Response.class)
				.doOnNext(System.out::println);

		StepVerifier.create(response)
				.expectNextCount(10)
				.verifyComplete();

	}

}
