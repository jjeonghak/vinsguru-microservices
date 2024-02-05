package com.vinsguru.webfluxpatterns.sec03.service;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.vinsguru.webfluxpatterns.sec03.dto.OrchestrationRequestContext;

import reactor.core.publisher.Mono;

public abstract class Orchestrator {

	public abstract Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx);
	public abstract Predicate<OrchestrationRequestContext> isSuccess();
	public abstract Consumer<OrchestrationRequestContext> cancel();

}
