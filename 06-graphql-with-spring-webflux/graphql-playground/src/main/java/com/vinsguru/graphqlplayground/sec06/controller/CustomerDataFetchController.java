package com.vinsguru.graphqlplayground.sec06.controller;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec06.dto.CustomerWithOrder;
import com.vinsguru.graphqlplayground.sec06.service.CustomerOrderDataFetcher;

import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class CustomerDataFetchController {

	private final CustomerOrderDataFetcher dataFetcher;

	// Query is root schema
	@SchemaMapping(typeName = "Query")
	public Flux<CustomerWithOrder> customers(DataFetchingFieldSelectionSet selectionSet) {
		return this.dataFetcher.customerOrders(selectionSet);
	}

	// @SchemaMapping(typeName = "Customer")
	// public Flux<CustomerOrder> orders(Customer customer) {
	// 	System.out.println("CustomerController: call orders by " + customer.getName());
	// 	return this.orderService.orderByCustomerName(customer.getName());
	// }

}
