package com.vinsguru.graphqlplayground.sec07.service;

import java.util.Collections;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec07.dto.CustomerWithOrder;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerOrderDataFetcher implements DataFetcher<Flux<CustomerWithOrder>> {

	private final CustomerService customerService;
	private final OrderService orderService;

	@Override
	public Flux<CustomerWithOrder> get(DataFetchingEnvironment environment) throws Exception {
		var includeOrders = environment.getSelectionSet().contains("orders");
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
