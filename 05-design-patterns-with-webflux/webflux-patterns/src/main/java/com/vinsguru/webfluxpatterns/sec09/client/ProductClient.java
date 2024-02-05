package com.vinsguru.webfluxpatterns.sec09.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec09.dto.Product;

import reactor.core.publisher.Mono;

@Service
public class ProductClient {

	private final WebClient client;

	public ProductClient(@Value("${sec09.service.product}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<Product> getProduct(Long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(Product.class)
				.timeout(Duration.ofMillis(500))
				.onErrorResume(ex -> Mono.empty());
	}

}
