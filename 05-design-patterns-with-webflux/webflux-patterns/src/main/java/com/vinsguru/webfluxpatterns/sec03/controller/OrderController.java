package com.vinsguru.webfluxpatterns.sec03.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxpatterns.sec03.dto.OrderRequest;
import com.vinsguru.webfluxpatterns.sec03.dto.OrderResponse;
import com.vinsguru.webfluxpatterns.sec03.service.OrchestratorService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sec03")
public class OrderController {

	private final OrchestratorService service;

	@PostMapping("/order")
	public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> requestMono) {
		return service.placeOrder(requestMono)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

}
