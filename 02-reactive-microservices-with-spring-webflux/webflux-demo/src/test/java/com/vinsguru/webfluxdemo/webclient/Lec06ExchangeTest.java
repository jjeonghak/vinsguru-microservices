package com.vinsguru.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;

import com.vinsguru.webfluxdemo.dtos.InputFailedValidationResponse;
import com.vinsguru.webfluxdemo.dtos.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

	//exchange = retrieve + additional info http status code
	@Test
	public void badRequestTest() {

		Mono<Response> response = webClient
				.get()
				.uri("/reactive/math/square/throw/{number}", 5)
				.exchangeToMono(this::exchange)
				.cast(Response.class)
				.doOnNext(System.out::println)
				.doOnError(err -> System.out.println(err.getMessage()));

		StepVerifier.create(response)
				.expectNextCount(1)
				.expectComplete();

	}

	private Mono<Object> exchange(ClientResponse cr) {
		if (cr.statusCode() == HttpStatusCode.valueOf(400)) {
			return cr.bodyToMono(InputFailedValidationResponse.class);
		} else {
			return cr.bodyToMono(Response.class);
		}
	}

}
