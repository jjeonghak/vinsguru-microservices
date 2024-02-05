package com.vinsguru.springrsocket.controller;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.springrsocket.dto.ClientConnectionRequest;
import com.vinsguru.springrsocket.service.MathClientManager;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ConnectionHandler {

	private final MathClientManager clientManager;

	// @ConnectMapping
	// public Mono<Void> handleConnection(ClientConnectionRequest request, RSocketRequester requester) {
	// 	System.out.println("ConnectionHandler: connection setup " + request);
	// 	return request.getSecretKey().equals("password") ? Mono.empty() :
	// 		// Mono.error(new RuntimeException("Invalid credentials"));
	// 		Mono.fromRunnable(() -> requester.rsocketClient().dispose());
	// }

	@ConnectMapping
	public Mono<Void> noEventConnection(RSocketRequester requester) {
		System.out.println("ConnectionHandler: no event connection setup");
		return Mono.empty();
	}

	@ConnectMapping("math.events.connection")
	public Mono<Void> mathEventConnection(RSocketRequester requester) {
		System.out.println("ConnectionHandler: math events connection setup");
		return Mono.fromRunnable(() -> this.clientManager.add(requester));
	}

}
