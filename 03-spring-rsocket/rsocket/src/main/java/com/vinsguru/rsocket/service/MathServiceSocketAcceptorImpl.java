package com.vinsguru.rsocket.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class MathServiceSocketAcceptorImpl implements SocketAcceptor {

	private static final String CREDENTIALS = "user:password";

	@Override
	public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload, RSocket rSocket) {
		System.out.println("MathServiceSocketAcceptorImpl: accept method");

		if (isValidClient(connectionSetupPayload.getDataUtf8())) {
			return Mono.fromCallable(MathService::new);
		}
		return Mono.fromCallable(FreeService::new);
	}

	private boolean isValidClient(String credentials) {
		return credentials.equals(CREDENTIALS);
	}

}
