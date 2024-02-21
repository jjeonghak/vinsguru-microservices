package com.vinsguru.productservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.productservice.entity.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
