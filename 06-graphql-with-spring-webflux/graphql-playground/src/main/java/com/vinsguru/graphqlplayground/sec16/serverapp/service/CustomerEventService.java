package com.vinsguru.graphqlplayground.sec16.serverapp.service;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec16.dto.CustomerEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class CustomerEventService {

	private final Sinks.Many<CustomerEvent> sink = Sinks.many().multicast().onBackpressureBuffer();
	private final Flux<CustomerEvent> flux = sink.asFlux().cache(0);

	public Flux<CustomerEvent> subscribe() {
		return this.flux;
	}

	public void emitEvent(CustomerEvent event) {
		this.sink.tryEmitNext(event);
	}

}
