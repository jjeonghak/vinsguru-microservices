package com.vinsguru.webfluxpatterns.sec10.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec10.dto.Review;

import reactor.core.publisher.Mono;

@Service
public class ReviewClient {

	private final WebClient client;

	public ReviewClient(@Value("${sec10.service.review}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<List<Review>> getReview(Long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
				.bodyToFlux(Review.class)
				.collectList();
	}

}
