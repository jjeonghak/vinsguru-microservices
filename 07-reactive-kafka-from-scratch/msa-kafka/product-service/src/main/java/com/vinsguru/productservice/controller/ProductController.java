package com.vinsguru.productservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService service;

	@GetMapping("/{productId}")
	public Mono<ResponseEntity<ProductDto>> view(@PathVariable("productId") Long productId) {
		return this.service.getProduct(productId)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

}
