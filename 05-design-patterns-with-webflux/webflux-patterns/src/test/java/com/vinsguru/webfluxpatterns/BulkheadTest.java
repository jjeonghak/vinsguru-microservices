package com.vinsguru.webfluxpatterns;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec10.dto.ProductAggregator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BulkheadTest {

	private WebClient client;

	@BeforeAll
	public void setClient() {
		this.client = WebClient.builder()
				.baseUrl("http://localhost:8080")
				.build();
	}

	@Test
	public void concurrentUsersTest() {
		StepVerifier.create(Flux.merge(fibRequests(), productRequests()))
				.verifyComplete();
	}

	private Mono<Void> fibRequests() {
		return Flux.range(1, 2)
				.flatMap(i -> this.client.get().uri("/sec10/fib/46").retrieve().bodyToMono(Long.class))
				.doOnNext(this::print)
				.then();
	}

	private Mono<Void> productRequests() {
		return Mono.delay(Duration.ofMillis(100))
				.thenMany(Flux.range(1, 2))
				.flatMap(i -> this.client.get().uri("/sec10/product/1").retrieve().bodyToMono(ProductAggregator.class))
				.map(ProductAggregator::getCategory)
				.doOnNext(this::print)
				.then();
	}

	private void print(Object o) {
		System.out.println(LocalDateTime.now() + " : " + o);
	}

}
