package com.vinsguru.redisperformance.service;

import org.springframework.stereotype.Service;

import com.vinsguru.redisperformance.entity.Product;
import com.vinsguru.redisperformance.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 {

	private final ProductRepository repository;

	public Mono<Product> getProduct(long id) {
		return repository.findById(id);
	}

	public Mono<Product> updateProduct(long id, Mono<Product> productMono) {
		return repository.findById(id)
				.flatMap(p -> productMono.doOnNext(pr -> pr.setId(id)))
				.doOnNext(repository::save);
	}

}
