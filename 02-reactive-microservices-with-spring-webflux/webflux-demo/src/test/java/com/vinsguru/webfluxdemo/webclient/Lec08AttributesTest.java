package com.vinsguru.webfluxdemo.webclient;

import org.junit.jupiter.api.Test;

import com.vinsguru.webfluxdemo.dtos.MultiplyRequestDto;
import com.vinsguru.webfluxdemo.dtos.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec08AttributesTest extends BaseTest {

	@Test
	public void headersTest() {

		Mono<Response> response = webClient
				.post()
				.uri("/reactive/math/multiply")
				.bodyValue(buildRequestDto(5, 2))
				// .attributes(m -> m.put("auth", "basic"))
				.attribute("auth", "basic")
				.retrieve()
				.bodyToMono(Response.class)
				.doOnNext(System.out::println);

		StepVerifier.create(response)
				.expectNextCount(1)
				.verifyComplete();

	}

	private MultiplyRequestDto buildRequestDto(int a, int b) {
		MultiplyRequestDto dto = new MultiplyRequestDto();
		dto.setFirst(a);
		dto.setSecond(b);
		return dto;
	}

}
