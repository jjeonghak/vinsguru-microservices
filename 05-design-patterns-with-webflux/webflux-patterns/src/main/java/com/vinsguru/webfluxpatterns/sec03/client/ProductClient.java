package com.vinsguru.webfluxpatterns.sec03.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec03.dto.Product;

import reactor.core.publisher.Mono;

@Service
public class ProductClient {

	private final WebClient client;

	public ProductClient(@Value("${sec03.service.product}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<Product> getProduct(long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(Product.class)
				.onErrorResume(ex -> Mono.empty());
	}

}
