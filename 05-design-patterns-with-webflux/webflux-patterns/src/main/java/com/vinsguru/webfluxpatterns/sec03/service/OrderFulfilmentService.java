package com.vinsguru.webfluxpatterns.sec03.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.vinsguru.webfluxpatterns.sec03.dto.Status;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderFulfilmentService {

	private final List<Orchestrator> orchestrators;

	public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
		List<Mono<OrchestrationRequestContext>> list = orchestrators.stream()
				.map(o -> o.create(ctx))
				.toList();

		return Mono.zip(list, a -> a[0])
				.cast(OrchestrationRequestContext.class)
				.doOnNext(this::updateStatus);
	}

	public void updateStatus(OrchestrationRequestContext ctx) {
		var success = this.orchestrators.stream().allMatch(o -> o.isSuccess().test(ctx));
		var status = success ? Status.SUCCESS : Status.FAILED;
		ctx.setStatus(status);
	}

}
