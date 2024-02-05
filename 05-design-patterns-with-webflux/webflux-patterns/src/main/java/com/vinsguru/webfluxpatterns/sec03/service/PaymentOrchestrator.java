package com.vinsguru.webfluxpatterns.sec03.service;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec03.client.UserClient;
import com.vinsguru.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.vinsguru.webfluxpatterns.sec03.dto.Status;

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
				.thenReturn(ctx);
	}

	@Override
	public Predicate<OrchestrationRequestContext> isSuccess() {
		return ctx -> ctx.getPaymentResponse().getStatus().equals(Status.SUCCESS);
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
