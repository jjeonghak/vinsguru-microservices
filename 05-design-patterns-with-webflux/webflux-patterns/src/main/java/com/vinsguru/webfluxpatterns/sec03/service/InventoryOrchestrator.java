package com.vinsguru.webfluxpatterns.sec03.service;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec03.client.InventoryClient;
import com.vinsguru.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.vinsguru.webfluxpatterns.sec03.dto.Status;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryOrchestrator extends Orchestrator {

	private final InventoryClient client;

	@Override
	public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
		return this.client.deduct(ctx.getInventoryRequest())
				.doOnNext(ctx::setInventoryResponse)
				.thenReturn(ctx);
	}

	@Override
	public Predicate<OrchestrationRequestContext> isSuccess() {
		return ctx -> ctx.getInventoryResponse().getStatus().equals(Status.SUCCESS);
	}

	@Override
	public Consumer<OrchestrationRequestContext> cancel() {
		return ctx -> Mono.just(ctx)
				.filter(isSuccess())
				.map(OrchestrationRequestContext::getInventoryRequest)
				.flatMap(this.client::restore)
				.subscribe();
	}

}
