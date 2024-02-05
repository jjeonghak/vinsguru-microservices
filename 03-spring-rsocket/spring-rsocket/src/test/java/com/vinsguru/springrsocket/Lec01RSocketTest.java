package com.vinsguru.springrsocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;

import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import com.vinsguru.springrsocket.dto.ComputationResponseDto;

import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Lec01RSocketTest {

	private RSocketRequester requester;

	@Autowired
	private RSocketRequester.Builder builder;

	@BeforeAll
	public void setup() {
		this.requester = this.builder.transport(TcpClientTransport.create("localhost", 6565));
	}

	@Test
	public void fireAndForget() {
		Mono<Void> mono = requester.route("math.service.print")
				.data(new ComputationRequestDto(5))
				.send();

		StepVerifier.create(mono)
				.verifyComplete();
	}

	@Test
	public void requestResponse() {
		Mono<ComputationResponseDto> mono = requester.route("math.service.square")
				.data(new ComputationRequestDto(5))
				.retrieveMono(ComputationResponseDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();
	}

	@Test
	public void requestStream() {
		Flux<ComputationResponseDto> flux = requester.route("math.service.table")
				.data(new ComputationRequestDto(5))
				.retrieveFlux(ComputationResponseDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(10)
				.verifyComplete();
	}

	@Test
	public void requestChannel() {
		Flux<ComputationRequestDto> dtoFlux = Flux.range(-10, 21)
				.map(ComputationRequestDto::new);

		Flux<ComputationResponseDto> flux = requester.route("math.service.chart")
				.data(dtoFlux)
				.retrieveFlux(ComputationResponseDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(21)
				.verifyComplete();
	}

}
