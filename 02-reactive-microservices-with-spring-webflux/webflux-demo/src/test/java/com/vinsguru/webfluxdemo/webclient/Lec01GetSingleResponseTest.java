package com.vinsguru.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;

import com.vinsguru.webfluxdemo.dtos.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {

	@Test
	public void blockTest() {

		Response response = webClient
				.get()
				.uri("/reactive/math/square/{number}", 5)
				.retrieve()
				.bodyToMono(Response.class)
				.block();

		System.out.println(response);

	}

	@Test
	public void stepVerifierTest() {

		Mono<Response> response = webClient
				.get()
				.uri("/reactive/math/square/{number}", 5)
				.retrieve()
				.bodyToMono(Response.class);

		StepVerifier.create(response)
				.expectNextMatches(r -> r.getOutput() == 25)
				.verifyComplete();

	}

}
