package com.vinsguru.graphqlplayground.sec04.controller;

import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec04.dto.CustomerOrder;
import com.vinsguru.graphqlplayground.sec04.dto.Customer;
import com.vinsguru.graphqlplayground.sec04.service.CustomerService;
import com.vinsguru.graphqlplayground.sec04.service.OrderService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;
	private final OrderService orderService;

	// Query is root schema
	@SchemaMapping(typeName = "Query")
	public Flux<Customer> customers() {
		return this.customerService.allCustomers();
	}

	// solved the (N + 1) problem
	// however it depends on output sequence
	// @BatchMapping(typeName = "Customer")
	// public Flux<List<CustomerOrder>> orders(List<Customer> customers) {
	// 	System.out.println("CustomerController: call orders by " + customers);
	// 	return this.orderService.orderByCustomerNames(
	// 			customers.stream().map(Customer::getName).collect(Collectors.toList())
	// 	);
	// }

	// solved the (N + 1) problem
	// it does not depend on output sequence
	@BatchMapping(typeName = "Customer")
	public Mono<Map<Customer, List<CustomerOrder>>> orders(List<Customer> customers) {
		System.out.println("CustomerController: call orders by " + customers);
		return this.orderService.fetchOrdersAsMap(customers);
	}

	@SchemaMapping(typeName = "Customer")
	public Mono<Integer> age() {
		return Mono.just(100);
	}

}
