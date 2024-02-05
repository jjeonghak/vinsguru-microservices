package com.vinsguru.webfluxpatterns.sec01.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec01.dto.Review;

import reactor.core.publisher.Mono;

@Service
public class ReviewClient {

	private final WebClient client;

	public ReviewClient(@Value("${sec01.service.review}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<List<Review>> getReview(Long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToFlux(Review.class)
				.collectList()
				.onErrorReturn(Collections.emptyList());
	}

}
