package com.vinsguru.productservice.repository;

import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.productservice.entity.Product;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

	Flux<Product> findByPriceBetween(int min, int max);

	Flux<Product> findByPriceBetween(Range<Integer> range);

}
