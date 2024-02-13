package com.vinsguru.graphqlplayground.sec05.controller;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec05.dto.Address;
import com.vinsguru.graphqlplayground.sec05.dto.Customer;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Controller
public class AddressController {

	@SchemaMapping(typeName = "Customer")
	public Mono<Address> address(Customer customer, DataFetchingEnvironment environment) {
		System.out.println("AddressController: address " + environment.getDocument());
		return Mono.just(
				Address.create(customer.getName() + " street", customer.getName() + " city")
		);
	}

}
