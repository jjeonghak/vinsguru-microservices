package com.vinsguru.springrsocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.RSocketRequester;

import com.vinsguru.springrsocket.dto.Response;

import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec03InputValidationTest {

	private RSocketRequester requester;

	@Autowired
	private RSocketRequester.Builder builder;

	@BeforeAll
	public void setup() {
		this.requester = this.builder.transport(TcpClientTransport.create("localhost", 6565));
	}

	@Test
	public void validation() {
		int input = 31;
		Mono<Integer> mono = requester.route("math.validation.double." + input)
				.retrieveMono(Integer.class)
				// .onErrorReturn(Integer.MIN_VALUE)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();
	}

	@Test
	public void response() {
		int input = 31;
		Mono<Response<Integer>> mono = requester.route("math.validation.double.response." + input)
				.retrieveMono(new ParameterizedTypeReference<Response<Integer>>() {})
				.doOnNext(r -> {
						if (r.hasError()) {
							System.out.println(r.getErrorResponse().getStatusCode().getDescription());
						} else {
							System.out.println(r.getSuccessResponse());
						}
				});

		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();
	}

}
