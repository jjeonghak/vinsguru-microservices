package com.vinsguru.userservice;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;

import com.vinsguru.userservice.dto.TransactionRequest;
import com.vinsguru.userservice.dto.TransactionResponse;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.dto.TransactionType;
import com.vinsguru.userservice.dto.UserDto;

import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTransactionTest {

	private RSocketRequester requester;

	@Autowired
	private RSocketRequester.Builder builder;

	@BeforeAll
	public void setRequester() {
		this.requester = builder
				.transport(TcpClientTransport.create("localhost", 7071));
	}

	@ParameterizedTest
	@MethodSource("testData")
	void transactionTest(int amount, TransactionType type, TransactionStatus status) {

		UserDto dto = getRandomUser();
		TransactionRequest request = new TransactionRequest(dto.getId(), amount, type);

		Mono<TransactionResponse> mono = requester.route("user.transaction")
				.data(request)
				.retrieveMono(TransactionResponse.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextMatches(r -> r.getStatus().equals(status))
				.verifyComplete();

	}

	private Stream<Arguments> testData() {
		return Stream.of(
				Arguments.of(2000, TransactionType.CREDIT, TransactionStatus.COMPLETED),
				Arguments.of(2000, TransactionType.DEBIT, TransactionStatus.COMPLETED),
				Arguments.of(12000, TransactionType.DEBIT, TransactionStatus.FAILED)
		);
	}

	private UserDto getRandomUser() {
		return requester.route("user.get.all")
				.retrieveFlux(UserDto.class)
				.next()
				.block();
	}

}
