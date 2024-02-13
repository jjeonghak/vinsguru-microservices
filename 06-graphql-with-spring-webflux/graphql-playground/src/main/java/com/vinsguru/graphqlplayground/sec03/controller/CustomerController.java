package com.vinsguru.graphqlplayground.sec03.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec03.dto.Customer;
import com.vinsguru.graphqlplayground.sec03.dto.CustomerOrder;
import com.vinsguru.graphqlplayground.sec03.service.CustomerService;
import com.vinsguru.graphqlplayground.sec03.service.OrderService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

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

	// it has the (N + 1) problem
	@SchemaMapping(typeName = "Customer")
	public Flux<CustomerOrder> orders(Customer customer, @Argument("limit") Integer limit) {
		System.out.println("CustomerController: call orders by " + customer.getName());
		return this.orderService.orderByCustomerName(customer.getName())
				.take(limit);
	}

}
