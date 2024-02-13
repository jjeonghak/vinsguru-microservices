package com.vinsguru.graphqlplayground.sec07.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec07.dto.Customer;

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
		return this.repository
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(c -> print("customer " + c.getName()));
	}

	private void print(String msg) {
		System.out.println("CustomerService: " + LocalDateTime.now() + " -> " + msg);
	}

}
