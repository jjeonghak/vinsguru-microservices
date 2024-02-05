package com.vinsguru.webfluxpatterns.sec08.client;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec08.dto.Review;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

@Service
public class ReviewClient {

	private final WebClient client;

	public ReviewClient(@Value("${sec08.service.review}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	@CircuitBreaker(name = "review-service", fallbackMethod = "fallback")
	public Mono<List<Review>> getReview(Long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
				.bodyToFlux(Review.class)
				.collectList()
				.retry(5)
				.timeout(Duration.ofMillis(300)); //TimeoutException
				// .onErrorReturn(Collections.emptyList());
	}

	public Mono<List<Review>> fallback(Long id, Throwable ex) {
		//can set up the expected Exception type in yml
		//TimeoutException or WebClientResponseException
		System.out.println("ReviewClient: fallback reviews called " + ex.getMessage());
		return Mono.just(Collections.emptyList());
	}

}
