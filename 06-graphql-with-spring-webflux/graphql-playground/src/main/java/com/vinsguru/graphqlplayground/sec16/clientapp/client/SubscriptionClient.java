package com.vinsguru.graphqlplayground.sec16.clientapp.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import com.vinsguru.graphqlplayground.sec16.dto.CustomerEvent;

import reactor.core.publisher.Flux;

@Service
public class SubscriptionClient {

	private final WebSocketGraphQlClient client;

	public SubscriptionClient(@Value("${customer.events.url.sec16}") String url) {
		this.client = WebSocketGraphQlClient.builder(url, new ReactorNettyWebSocketClient())
				.build();
	}

	public Flux<CustomerEvent> customerEvents() {
		var doc = """
				subscription {
					customerEvents {
						id
						action
					}
				}
				""";
		return this.client.document(doc)
				.retrieveSubscription("customerEvents")
				.toEntity(CustomerEvent.class);
	}

}
