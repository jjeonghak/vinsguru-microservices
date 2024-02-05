package com.vinsguru.rsocket.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class FastProducerSocketAcceptorImpl implements SocketAcceptor {

	@Override
	public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload, RSocket rSocket) {
		System.out.println("FastProducerSocketAcceptorImpl: accept method");
		return Mono.fromCallable(FastProducerService::new);
	}

}
