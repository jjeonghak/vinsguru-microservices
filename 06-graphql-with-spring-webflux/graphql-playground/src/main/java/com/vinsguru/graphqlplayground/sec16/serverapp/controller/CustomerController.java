package com.vinsguru.graphqlplayground.sec16.serverapp.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec16.dto.CustomerDto;
import com.vinsguru.graphqlplayground.sec16.dto.CustomerNotFound;
import com.vinsguru.graphqlplayground.sec16.dto.CustomerResponse;
import com.vinsguru.graphqlplayground.sec16.dto.DeleteResponseDto;
import com.vinsguru.graphqlplayground.sec16.serverapp.service.CustomerService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;

	@QueryMapping("customers")
	public Flux<CustomerDto> customers() {
		return this.service.allCustomers();
	}

	@QueryMapping("customerById")
	public Mono<CustomerDto> customerById(@Argument("id") Long id) {
		return this.service.customerById(id)
				.switchIfEmpty(Mono.error(new RuntimeException("Not found user by " + id)));
	}

	@QueryMapping("customerByIdWithUnion")
	public Mono<Object> customerByIdWithUnion(@Argument("id") Long id) {
		return this.service.customerById(id)
				.cast(Object.class)
				.defaultIfEmpty(CustomerNotFound.create(id, "Not found user"));
	}

	@MutationMapping("createCustomer")
	public Mono<CustomerDto> createCustomer(@Argument("customer") CustomerDto dto) {
		return this.service.createCustomer(dto);
	}

	@MutationMapping("updateCustomer")
	public Mono<CustomerDto> updateCustomer(
			@Argument("id") Long id,
			@Argument("customer") CustomerDto dto) {
		return this.service.updateCustomer(id, dto);
	}

	@MutationMapping("deleteCustomer")
	public Mono<DeleteResponseDto> deleteCustomer(@Argument("id") Long id) {
		return this.service.deleteCustomer(id);
	}

}
