package com.vinsguru.graphqlplayground.sec14.controller;

import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec14.dto.CustomerEvent;
import com.vinsguru.graphqlplayground.sec14.service.CustomerEventService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class CustomerEventController {

	private final CustomerEventService service;

	@SubscriptionMapping("customerEvents")
	public Flux<CustomerEvent> customerEvents() {
		return this.service.subscribe();
	}

}
