package com.vinsguru.graphqlplayground.sec03.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec03.dto.CustomerOrder;

import reactor.core.publisher.Flux;

@Service
public class OrderService {

	private final Map<String, List<CustomerOrder>> map = Map.of(
			"sam", List.of(
					CustomerOrder.create(UUID.randomUUID().toString(), "sam-product-1"),
					CustomerOrder.create(UUID.randomUUID().toString(), "sam-product-2")
			),
			"mike", List.of(
					CustomerOrder.create(UUID.randomUUID().toString(), "mike-product-1"),
					CustomerOrder.create(UUID.randomUUID().toString(), "mike-product-2"),
					CustomerOrder.create(UUID.randomUUID().toString(), "mike-product-3")
			)
	);

	public Flux<CustomerOrder> orderByCustomerName(String name) {
		return Flux.fromIterable(map.getOrDefault(name, Collections.emptyList()));
	}

}
