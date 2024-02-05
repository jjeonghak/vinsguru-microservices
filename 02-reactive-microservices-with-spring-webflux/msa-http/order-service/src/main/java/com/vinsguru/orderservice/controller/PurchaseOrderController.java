package com.vinsguru.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.vinsguru.orderservice.dto.PurchaseOrderRequestDto;
import com.vinsguru.orderservice.dto.PurchaseOrderResponseDto;
import com.vinsguru.orderservice.service.OrderFulfillmentService;
import com.vinsguru.orderservice.service.OrderQueryService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class PurchaseOrderController {

	private final OrderFulfillmentService orderFulfillmentService;
	private final OrderQueryService queryService;

	@PostMapping
	public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono) {
		return orderFulfillmentService.processOrder(requestDtoMono)
				.map(ResponseEntity::ok)
				.onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
				.onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
	}

	@GetMapping("/user/{userId}")
	public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable("userId") long userId) {
		return queryService.getProductsByUserId(userId);
	}

}
