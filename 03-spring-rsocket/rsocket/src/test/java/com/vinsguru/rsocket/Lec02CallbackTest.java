package com.vinsguru.rsocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.vinsguru.rsocket.client.CallbackService;
import com.vinsguru.rsocket.dto.RequestDto;
import com.vinsguru.rsocket.util.ObjectUtil;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec02CallbackTest {

	private RSocket rSocket;

	@BeforeAll
	public void setup() {
		this.rSocket = RSocketConnector.create()
				.acceptor(SocketAcceptor.with(new CallbackService()))
				.connect(TcpClientTransport.create("localhost", 6570))
				.block();
	}

	@Test
	public void callback() throws InterruptedException {

		RequestDto requestDto = new RequestDto(5);
		Mono<Void> mono = rSocket.fireAndForget(ObjectUtil.toPayload(requestDto));

		StepVerifier.create(mono)
				.verifyComplete();

		System.out.println("Lec02CallbackTest: going to wait");

		Thread.sleep(12000);

	}

}
