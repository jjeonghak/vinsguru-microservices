package com.vinsguru.analyticsservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.analyticsservice.entity.ProductViewCount;

import reactor.core.publisher.Flux;

@Repository
public interface ProductViewRepository extends ReactiveCrudRepository<ProductViewCount, Long> {

	Flux<ProductViewCount> findTop5ByOrderByCountDesc();

}
