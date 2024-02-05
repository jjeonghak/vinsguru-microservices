package com.vinsguru.springrsocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;

import com.vinsguru.springrsocket.dto.ComputationRequestDto;

import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec02DestinationVariableTest {

	private RSocketRequester requester;

	@Autowired
	private RSocketRequester.Builder builder;

	@BeforeAll
	public void setup() {
		this.requester = this.builder.transport(TcpClientTransport.create("localhost", 6565));
	}

	@Test
	public void fireAndForget() {
		int input = 55;
		Mono<Void> mono = requester.route("math.service.print." + input).send();

		StepVerifier.create(mono)
				.verifyComplete();
	}

}
