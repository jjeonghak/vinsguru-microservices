package com.vinsguru.rsocket;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec03BackpressureTest {

	private RSocket rSocket;

	@BeforeAll
	public void setup() {
		this.rSocket = RSocketConnector.create()
				.connect(TcpClientTransport.create("localhost", 6575))
				.block();
	}

	@Test
	public void backpressure() throws InterruptedException {

		Flux<String> flux = rSocket.requestStream(DefaultPayload.create(""))
				.map(Payload::getDataUtf8)
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(d -> System.out.println("Lec03BackpressureTest: client received " + d));

		StepVerifier.create(flux)
				.expectNextCount(1000)
				.verifyComplete();

	}

}
