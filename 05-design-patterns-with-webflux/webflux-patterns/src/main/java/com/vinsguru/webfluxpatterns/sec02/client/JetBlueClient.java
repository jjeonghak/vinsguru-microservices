package com.vinsguru.webfluxpatterns.sec02.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec02.dto.FlightResult;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JetBlueClient {

	private static final String JETBLUE = "JETBLUE";
	private final WebClient client;

	public JetBlueClient(@Value("${sec02.service.jetblue}") String url) {
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
				.doOnNext(fr -> this.normalizeResponse(fr, from, to))
				.onErrorResume(ex -> Mono.empty());
	}

	private void normalizeResponse(FlightResult result, String from, String to) {
		result.setFrom(from);
		result.setTo(to);
		result.setAirline(JETBLUE);
	}

}
