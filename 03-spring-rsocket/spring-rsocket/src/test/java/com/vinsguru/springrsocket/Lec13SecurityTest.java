package com.vinsguru.springrsocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import com.vinsguru.springrsocket.dto.ComputationResponseDto;

import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec13SecurityTest {

	private RSocketRequester requester;

	@Autowired
	private RSocketRequester.Builder builder;

	@BeforeAll
	public void connectionSetup() {
		//microservice client metadata
		UsernamePasswordMetadata metadata = new UsernamePasswordMetadata("client", "password");
		MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

		this.requester = builder
				.setupMetadata(metadata, mimeType)
				.transport(TcpClientTransport.create("localhost", 6565));
	}


	@Test
	public void requestResponse() {

		//user client metadata
		UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("user", "password");
		MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

		Mono<ComputationResponseDto> mono = requester.route("math.service.secured.square")
				.metadata(credentials, mimeType)
				.data(new ComputationRequestDto(5))
				.retrieveMono(ComputationResponseDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();

	}

	@Test
	public void requestStream() {

		//user client metadata
		UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("admin", "password");
		MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

		Flux<ComputationResponseDto> flux = requester.route("math.service.secured.table")
				.metadata(credentials, mimeType)
				.data(new ComputationRequestDto(5))
				.retrieveFlux(ComputationResponseDto.class)
				.doOnNext(System.out::println)
				.take(3);

		StepVerifier.create(flux)
				.expectNextCount(3)
				.verifyComplete();
	}

}
