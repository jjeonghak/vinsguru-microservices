package com.vinsguru.webfluxpatterns.sec05.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec05.dto.CarReservationRequest;
import com.vinsguru.webfluxpatterns.sec05.dto.CarReservationResponse;

import reactor.core.publisher.Flux;

@Service
public class CarClient {

	private final WebClient client;

	public CarClient(@Value("${sec05.service.car}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> requestFlux) {
		return this.client
				.post()
				.body(requestFlux, CarReservationRequest.class)
				.retrieve()
				.bodyToFlux(CarReservationResponse.class)
				.onErrorResume(ex -> Flux.empty());
	}

}
