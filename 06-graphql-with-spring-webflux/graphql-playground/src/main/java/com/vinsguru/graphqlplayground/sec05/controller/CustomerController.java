package com.vinsguru.graphqlplayground.sec05.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec05.dto.Customer;
import com.vinsguru.graphqlplayground.sec05.service.CustomerService;

import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;

	@QueryMapping("customers")
	public Flux<Customer> customers(DataFetchingEnvironment environment) {
		System.out.println("CustomerController: customer " + environment.getDocument());
		return this.service.allCustomers();
	}

}
