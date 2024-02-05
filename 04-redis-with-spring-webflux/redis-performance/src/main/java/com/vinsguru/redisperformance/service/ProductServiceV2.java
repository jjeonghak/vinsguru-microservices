package com.vinsguru.redisperformance.service;

import org.springframework.stereotype.Service;

import com.vinsguru.redisperformance.entity.Product;
import com.vinsguru.redisperformance.service.util.CacheTemplate;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceV2 {

	private final CacheTemplate<Long, Product> cacheTemplate;
	private final ProductVisitService visitService;

	public Mono<Product> getProduct(long id) {
		return this.cacheTemplate.get(id)
				.doFirst(() -> this.visitService.addVisit(id));
	}

	public Mono<Product> updateProduct(long id, Mono<Product> productMono) {
		return productMono
				.flatMap(p -> this.cacheTemplate.update(id, p));
	}

	public Mono<Void> deleteProduct(long id) {
		return this.cacheTemplate.delete(id);
	}

}
