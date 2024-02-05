package com.vinsguru.webfluxpatterns.sec09.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sec09")
public class CalculatorController {

	//CPU intensive
	//5req/20sec
	@GetMapping("/calculator/{input}")
	@RateLimiter(name = "calculator-service", fallbackMethod = "fallback")
	public Mono<ResponseEntity<Long>> doubleInput(@PathVariable("input") Integer input) {
		return Mono.fromSupplier(() -> input * 2L)
				.map(ResponseEntity::ok);
	}

	public Mono<ResponseEntity<String>> fallback(Integer input, Throwable ex) {
		return Mono.just(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage()));
	}

}
