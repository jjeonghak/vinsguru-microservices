package com.vinsguru.graphqlplayground.sec06.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.graphqlplayground.sec06.dto.CustomerWithOrder;
import com.vinsguru.graphqlplayground.sec06.service.CustomerService;
import com.vinsguru.graphqlplayground.sec06.service.OrderService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CustomerRestController {

	private final CustomerService customerService;
	private final OrderService orderService;

	@GetMapping("customers")
	public Flux<CustomerWithOrder> customerOrders() {
		return this.customerService.allCustomers()
			.flatMap(c ->
					this.orderService.orderByCustomerName(c.getName())
							.collectList()
							.map(l -> CustomerWithOrder.create(c, l))
			);
	}

}
