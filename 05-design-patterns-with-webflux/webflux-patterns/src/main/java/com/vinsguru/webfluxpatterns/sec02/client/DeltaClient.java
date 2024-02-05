package com.vinsguru.webfluxpatterns.sec02.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec02.dto.FlightResult;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DeltaClient {

	private final WebClient client;

	public DeltaClient(@Value("${sec02.service.delta}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Flux<FlightResult> getFlights(String from, String to) {
		return this.client
				.get()
				.uri("/{from}/{to}", from, to)
				.retrieve()
				.bodyToFlux(FlightResult.class)
				.onErrorResume(ex -> Mono.empty());
	}

}
