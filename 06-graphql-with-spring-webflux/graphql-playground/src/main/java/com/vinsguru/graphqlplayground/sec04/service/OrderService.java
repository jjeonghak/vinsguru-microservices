package com.vinsguru.graphqlplayground.sec04.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec04.dto.CustomerOrder;
import com.vinsguru.graphqlplayground.sec04.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

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

	// public Flux<List<CustomerOrder>> orderByCustomerNames(List<String> names) {
	// 	return Flux.fromIterable(names)
	// 			// flatMap and concatMap method does not emit empty item
	// 			.flatMapSequential(n -> fetchOrders(n).defaultIfEmpty(Collections.emptyList()));
	// }

	// private Mono<List<CustomerOrder>> fetchOrders(String name) {
	// 	return Mono.justOrEmpty(map.get(name))
	// 			.delayElement(Duration.ofMillis(ThreadLocalRandom.current().nextInt(0, 500)));
	// }

	public Mono<Map<Customer, List<CustomerOrder>>> fetchOrdersAsMap(List<Customer> customers) {
		return Flux.fromIterable(customers)
				.map(c -> Tuples.of(c, map.getOrDefault(c.getName(), Collections.emptyList())))
				.collectMap(
						Tuple2::getT1,
						Tuple2::getT2
				);
	}

}
