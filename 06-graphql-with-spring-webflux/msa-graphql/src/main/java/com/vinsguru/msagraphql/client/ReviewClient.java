package com.vinsguru.msagraphql.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.msagraphql.dto.Review;

import reactor.core.publisher.Flux;

@Service
public class ReviewClient {

	private final WebClient client;

	public ReviewClient(@Value("${service.url.review}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Flux<Review> reviews(Long movieId) {
		return this.client.get()
				.uri("/{id}", movieId)
				.retrieve()
				.bodyToFlux(Review.class);
	}

}
