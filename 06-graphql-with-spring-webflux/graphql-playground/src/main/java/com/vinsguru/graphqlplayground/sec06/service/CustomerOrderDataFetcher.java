package com.vinsguru.graphqlplayground.sec06.service;

import java.util.Collections;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec06.dto.CustomerWithOrder;

import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerOrderDataFetcher {

	private final CustomerService customerService;
	private final OrderService orderService;

	public Flux<CustomerWithOrder> customerOrders(DataFetchingFieldSelectionSet selectionSet) {
		var includeOrders = selectionSet.contains("orders");
		System.out.println(includeOrders);

		return this.customerService.allCustomers()
				.map(c -> CustomerWithOrder.create(c, Collections.emptyList()))
				.transform(updateOrdersTransformer(includeOrders));
	}

	private UnaryOperator<Flux<CustomerWithOrder>> updateOrdersTransformer(boolean includeOrders) {
		return includeOrders ? f -> f.flatMapSequential(this::fetchOrders) : f -> f;
	}

	private Mono<CustomerWithOrder> fetchOrders(CustomerWithOrder customerWithOrder) {
		return this.orderService.orderByCustomerName(customerWithOrder.getName())
				.collectList()
				.doOnNext(customerWithOrder::setOrders)
				.thenReturn(customerWithOrder);
	}

}
