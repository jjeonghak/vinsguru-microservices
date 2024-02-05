package com.vinsguru.webfluxpatterns.sec01.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxpatterns.sec01.dto.ProductAggregator;
import com.vinsguru.webfluxpatterns.sec01.service.ProductAggregatorService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sec01")
public class ProductAggregateController {

	private final ProductAggregatorService service;

	@GetMapping("/product/{id}")
	public Mono<ResponseEntity<ProductAggregator>> getProductAggregate(@PathVariable("id") long id) {
		return service.aggregate(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

}
