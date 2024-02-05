package com.vinsguru.webfluxpatterns.sec01.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec01.dto.ProductResponse;

import reactor.core.publisher.Mono;

@Service
public class ProductClient {

	private final WebClient client;

	public ProductClient(@Value("${sec01.service.product}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<ProductResponse> getProduct(Long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(ProductResponse.class)
				.onErrorResume(ex -> Mono.empty());
	}

}
