package com.vinsguru.rsocket;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.vinsguru.rsocket.dto.ChartResponseDto;
import com.vinsguru.rsocket.dto.RequestDto;
import com.vinsguru.rsocket.dto.ResponseDto;
import com.vinsguru.rsocket.util.ObjectUtil;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec01RSocketTest {

	private RSocket rSocket;

	@BeforeAll
	public void setup() {
		this.rSocket = RSocketConnector.create()
				.connect(TcpClientTransport.create("localhost", 6565))
				.block();
	}

	@Test
	public void fireAndForget() {

		Payload payload = ObjectUtil.toPayload(new RequestDto());
		Mono<Void> mono = rSocket.fireAndForget(payload);

		StepVerifier.create(mono)
				.verifyComplete();

	}

	@Test
	public void requestResponse() {

		Payload payload = ObjectUtil.toPayload(new RequestDto(5));
		Mono<ResponseDto> mono = rSocket.requestResponse(payload)
				.map(p -> ObjectUtil.toObject(p, ResponseDto.class))
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();

	}

	@Test
	public void requestStream() {

		Payload payload = ObjectUtil.toPayload(new RequestDto(5));
		Flux<ResponseDto> flux = rSocket.requestStream(payload)
				.map(p -> ObjectUtil.toObject(p, ResponseDto.class))
				.doOnNext(System.out::println)
				.take(4);

		StepVerifier.create(flux)
				.expectNextCount(4)
				.verifyComplete();

	}

	@Test
	public void requestChannel() {

		Flux<Payload> payloads = Flux.range(-10, 21)
				.delayElements(Duration.ofMillis(500))
				.map(RequestDto::new)
				.map(ObjectUtil::toPayload);

		Flux<ChartResponseDto> flux = rSocket.requestChannel(payloads)
				.map(p -> ObjectUtil.toObject(p, ChartResponseDto.class))
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(21)
				.verifyComplete();

	}

}
