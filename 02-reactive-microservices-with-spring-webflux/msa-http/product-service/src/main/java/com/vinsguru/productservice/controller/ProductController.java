package com.vinsguru.productservice.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

	private final ProductService service;

	@GetMapping("/all")
	public Flux<ProductDto> all() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable("id") String id) {
		simulateException();
		return service.getProductById(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@PostMapping
	public Mono<ProductDto> insertProduct(@RequestBody Mono<ProductDto> productDtoMono) {
		return service.insertProduct(productDtoMono);
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<ProductDto>> updateProduct(
				@PathVariable("id") String id,
				@RequestBody Mono<ProductDto> productDtoMono) {
		return service.updateProduct(id, productDtoMono)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteProduct(@PathVariable("id") String id) {
		return service.deleteProduct(id);
	}

	@GetMapping("/price/range")
	public Flux<ProductDto> getByPriceRange(
				@RequestParam("min") int min,
				@RequestParam("max") int max) {
		return service.getProductByPriceRange(min, max);
	}

	private void simulateException() {
		int random = ThreadLocalRandom.current().nextInt(1, 10);
		if (random > 5) {
			throw new RuntimeException("something is wrong");
		}
	}

}
