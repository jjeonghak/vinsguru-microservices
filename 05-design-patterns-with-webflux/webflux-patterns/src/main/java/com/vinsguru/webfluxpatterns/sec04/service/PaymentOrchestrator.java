package com.vinsguru.webfluxpatterns.sec04.service;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec04.client.UserClient;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentOrchestrator extends Orchestrator {

	private final UserClient client;

	@Override
	public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
		return this.client.deduct(ctx.getPaymentRequest())
				.doOnNext(ctx::setPaymentResponse)
				.thenReturn(ctx)
				.handle(this.statusHandler());
	}

	@Override
	public Predicate<OrchestrationRequestContext> isSuccess() {
		return ctx -> Objects.nonNull(ctx.getPaymentResponse()) && ctx.getPaymentResponse().getStatus().equals(Status.SUCCESS);
	}

	@Override
	public Consumer<OrchestrationRequestContext> cancel() {
		return ctx -> Mono.just(ctx)
				.filter(isSuccess())
				.map(OrchestrationRequestContext::getPaymentRequest)
				.flatMap(this.client::refund)
				.subscribe();
	}

}
