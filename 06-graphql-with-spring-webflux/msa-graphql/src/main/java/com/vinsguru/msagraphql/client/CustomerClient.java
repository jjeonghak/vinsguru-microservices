package com.vinsguru.msagraphql.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.msagraphql.dto.Customer;
import com.vinsguru.msagraphql.dto.CustomerInput;
import com.vinsguru.msagraphql.dto.WatchListInput;

import reactor.core.publisher.Mono;

@Service
public class CustomerClient {

	private final WebClient client;

	public CustomerClient(@Value("${service.url.customer}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<Customer> getCustomer(Long id) {
		return this.client.get()
				.uri("/{id}", id)
				.retrieve()
				.bodyToMono(Customer.class);
	}

	public Mono<Customer> updateCustomer(CustomerInput input) {
		return this.client.put()
				.uri("/{id}", input.getId())
				.bodyValue(input)
				.retrieve()
				.bodyToMono(Customer.class);
	}

	public Mono<List<Long>> addToWatchList(WatchListInput input) {
		return this.client.post()
				.uri("/watchlist")
				.bodyValue(input)
				.retrieve()
				.bodyToFlux(Long.class)
				.collectList();
	}

}
