package com.vinsguru.webfluxpatterns.sec05.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec05.dto.RoomReservationRequest;
import com.vinsguru.webfluxpatterns.sec05.dto.RoomReservationResponse;

import reactor.core.publisher.Flux;

@Service
public class RoomClient {

	private final WebClient client;

	public RoomClient(@Value("${sec05.service.room}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> requestFlux) {
		return this.client
				.post()
				.body(requestFlux, RoomReservationRequest.class)
				.retrieve()
				.bodyToFlux(RoomReservationResponse.class)
				.onErrorResume(ex -> Flux.empty());
	}

}
