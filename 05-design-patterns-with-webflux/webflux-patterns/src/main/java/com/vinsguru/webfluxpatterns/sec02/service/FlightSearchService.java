package com.vinsguru.webfluxpatterns.sec02.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec02.client.DeltaClient;
import com.vinsguru.webfluxpatterns.sec02.client.FrontierClient;
import com.vinsguru.webfluxpatterns.sec02.client.JetBlueClient;
import com.vinsguru.webfluxpatterns.sec02.dto.FlightResult;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

	private final DeltaClient deltaClient;
	private final FrontierClient frontierClient;
	private final JetBlueClient jetBlueClient;

	public Flux<FlightResult> getFlights(String from, String to) {
		return Flux.merge(
				this.deltaClient.getFlights(from, to),
				this.frontierClient.getFlights(from, to),
				this.jetBlueClient.getFlights(from, to)
		)
		.take(Duration.ofSeconds(5));
	}

}
