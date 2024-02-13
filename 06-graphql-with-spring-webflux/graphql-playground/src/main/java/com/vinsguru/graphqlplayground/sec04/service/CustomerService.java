package com.vinsguru.graphqlplayground.sec04.service;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec04.dto.Customer;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {

	private final Flux<Customer> repository = Flux.just(
			Customer.create(1L, "sam", 20, "atlanta"),
			Customer.create(2L, "jake", 10, "las vegas"),
			Customer.create(3L, "mike", 25, "miami"),
			Customer.create(4L, "john", 17, "houston")
	);

	public Flux<Customer> allCustomers() {
		return this.repository;
	}

}
