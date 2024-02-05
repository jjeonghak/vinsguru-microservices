package com.vinsguru.webfluxdemo.webclient;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec07QueryParamsTest extends BaseTest {

	@Test
	public void queryParamsTest() {

		String QUERY = "http://localhost:8080/jobs/search?pageSize={pageSize}&pageNumber={pageNumber}";
		URI uri = UriComponentsBuilder.fromUriString(QUERY)
				.build(10, 20);

		Map<String, Integer> params = Map.of(
				"pageSize", 10,
				"pageNumber", 20
		);

		Flux<Integer> response = webClient
				.get()
				// .uri(uri)
				// .uri(b -> b.path("/jobs/search").query("pageSize={pageSize}&pageNumber={pageNumber}").build(10, 20))
				.uri(b -> b.path("/jobs/search").query("pageSize={pageSize}&pageNumber={pageNumber}").build(params))
				.retrieve()
				.bodyToFlux(Integer.class)
				.doOnNext(System.out::println);

		StepVerifier.create(response)
				.expectNext(10)
				.expectNext(20)
				.verifyComplete();

	}

}
