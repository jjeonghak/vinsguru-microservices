package com.vinsguru.webfluxpatterns.sec04.service;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec04.client.ShippingClient;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShippingOrchestrator extends Orchestrator {

	private final ShippingClient client;

	@Override
	public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
		return this.client.schedule(ctx.getShippingRequest())
				.doOnNext(ctx::setShippingResponse)
				.thenReturn(ctx)
				.handle(this.statusHandler());
	}

	@Override
	public Predicate<OrchestrationRequestContext> isSuccess() {
		return ctx -> Objects.nonNull(ctx.getShippingResponse()) && ctx.getShippingResponse().getStatus().equals(Status.SUCCESS);
	}

	@Override
	public Consumer<OrchestrationRequestContext> cancel() {
		return ctx -> Mono.just(ctx)
				.filter(isSuccess())
				.map(OrchestrationRequestContext::getShippingRequest)
				.flatMap(this.client::cancel)
				.subscribe();
	}

}
