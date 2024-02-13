package com.vinsguru.graphqlplayground.sec02.service;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec02.dto.AgeRangeFilter;
import com.vinsguru.graphqlplayground.sec02.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	public Mono<Customer> customerById(Long id) {
		return this.repository
				.filter(c -> c.getId().equals(id))
				.next();
	}

	public Flux<Customer> nameContains(String name) {
		return this.repository
				.filter(c -> c.getName().contains(name));
	}

	public Flux<Customer> withinAge(AgeRangeFilter filter) {
		return this.repository
				.filter(c -> filter.isBetween(c.getAge()));
	}

}
