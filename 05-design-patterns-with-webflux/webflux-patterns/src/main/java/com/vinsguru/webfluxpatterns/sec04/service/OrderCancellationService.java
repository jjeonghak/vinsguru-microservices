package com.vinsguru.webfluxpatterns.sec04.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec04.dto.OrchestrationRequestContext;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderCancellationService {

	private Sinks.Many<OrchestrationRequestContext> sink;
	private Flux<OrchestrationRequestContext> flux;

	private final List<Orchestrator> orchestrators;

	@PostConstruct
	public void init() {
		this.sink = Sinks.many().multicast().onBackpressureBuffer();
		this.flux = this.sink.asFlux().publishOn(Schedulers.boundedElastic());
		orchestrators.forEach(o -> this.flux.subscribe(o.cancel()));
	}

	public void cancelOrder(OrchestrationRequestContext ctx) {
		this.sink.tryEmitNext(ctx);
	}

}
