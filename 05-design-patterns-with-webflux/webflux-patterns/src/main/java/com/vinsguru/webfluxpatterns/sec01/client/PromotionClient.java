package com.vinsguru.webfluxpatterns.sec01.client;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec01.dto.PromotionResponse;

import reactor.core.publisher.Mono;

@Service
public class PromotionClient {

	private static final PromotionResponse NONE = PromotionResponse.create(
			-1L, "none", .0, LocalDate.now()
	);

	private final WebClient client;

	public PromotionClient(@Value("${sec01.service.promotion}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<PromotionResponse> getPromotion(Long id) {
		return this.client
				.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(PromotionResponse.class)
				.onErrorReturn(NONE);
	}

}
