package com.vinsguru.redisperformance.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.redisperformance.entity.Product;
import com.vinsguru.redisperformance.service.ProductServiceV2;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/v2")
public class ProductControllerV2 {

	private final ProductServiceV2 service;

	@GetMapping("/{id}")
	public Mono<Product> getProduct(@PathVariable("id") long id) {
		return service.getProduct(id);
	}

	@PutMapping("/{id}")
	public Mono<Product> updateProduct(
			@PathVariable("id") long id,
			@RequestBody Mono<Product> productMono) {
		return service.updateProduct(id, productMono);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteProduct(@PathVariable("id") long id) {
		return service.deleteProduct(id);
	}

}
