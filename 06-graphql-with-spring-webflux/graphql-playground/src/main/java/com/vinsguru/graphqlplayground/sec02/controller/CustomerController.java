package com.vinsguru.graphqlplayground.sec02.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec02.dto.Customer;
import com.vinsguru.graphqlplayground.sec02.dto.AgeRangeFilter;
import com.vinsguru.graphqlplayground.sec02.service.CustomerService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;

	@QueryMapping("customers")
	public Flux<Customer> customers() {
		return this.service.allCustomers();
	}

	@QueryMapping("customerById")
	public Mono<Customer> customerById(@Argument("id") Long id) {
		return this.service.customerById(id);
	}

	@QueryMapping("customersNameContains")
	public Flux<Customer> customersNameContains(@Argument("name") String name) {
		return this.service.nameContains(name);
	}

	@QueryMapping("customersByAgeRange")
	public Flux<Customer> customersByAgeRange(@Argument("filter") AgeRangeFilter filter) {
		return this.service.withinAge(filter);
	}

}
