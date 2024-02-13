package com.vinsguru.msagraphql.controller;

import java.util.Collections;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.msagraphql.client.CustomerClient;
import com.vinsguru.msagraphql.dto.Customer;
import com.vinsguru.msagraphql.dto.CustomerInput;
import com.vinsguru.msagraphql.dto.Status;
import com.vinsguru.msagraphql.dto.WatchListInput;
import com.vinsguru.msagraphql.dto.WatchListResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerClient client;

	@QueryMapping("userProfile")
	public Mono<Customer> userProfile(@Argument("id") Long id) {
		return this.client.getCustomer(id);
	}

	@MutationMapping("updateProfile")
	public Mono<Customer> updateProfile(@Argument("input") CustomerInput input) {
		return this.client.updateCustomer(input);
	}

	@MutationMapping("addToWatchList")
	public Mono<WatchListResponse> addToWatchList(@Argument("input") WatchListInput input) {
		return this.client.addToWatchList(input)
				.map(list -> WatchListResponse.create(Status.SUCCESS, list))
				.onErrorReturn(WatchListResponse.create(Status.FAILURE, Collections.emptyList()));
	}

}
