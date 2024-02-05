package com.vinsguru.redisperformance.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.redisperformance.entity.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
