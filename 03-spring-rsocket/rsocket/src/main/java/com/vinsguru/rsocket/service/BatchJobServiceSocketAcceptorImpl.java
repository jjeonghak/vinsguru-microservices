package com.vinsguru.rsocket.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class BatchJobServiceSocketAcceptorImpl implements SocketAcceptor {

	@Override
	public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload, RSocket rSocket) {
		System.out.println("BatchJobServiceSocketAcceptorImpl: accept method");
		return Mono.just(new BatchJobService(rSocket));
	}

}
