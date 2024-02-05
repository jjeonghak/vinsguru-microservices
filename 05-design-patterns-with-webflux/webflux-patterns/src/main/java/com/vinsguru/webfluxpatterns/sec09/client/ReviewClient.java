package com.vinsguru.webfluxpatterns.sec09.client;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec09.dto.Review;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class ReviewClient {

	private final WebClient client;

	public ReviewClient(@Value("${sec10.service.review}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	@RateLimiter(name = "review-service", fallbackMethod = "fallback")
	public Mono<List<Review>> getReview(Long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
				.bodyToFlux(Review.class)
				.collectList();
				// .doOnNext(list -> ); //put in cache
	}

	public Mono<List<Review>> fallback(Long id, Throwable ex) {
		// return Mono.fromSupplier(() -> ); //read from cache
		return Mono.just(Collections.emptyList());
	}

}
