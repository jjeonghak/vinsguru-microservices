package com.vinsguru.userservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import com.vinsguru.userservice.dto.OperationType;
import com.vinsguru.userservice.dto.UserDto;

import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserCrudTests {

	private RSocketRequester requester;

	@Autowired
	private RSocketRequester.Builder builder;

	@BeforeAll
	public void setRequester() {
		this.requester = builder
				.transport(TcpClientTransport.create("localhost", 7071));
	}

	@Test
	void getAllUsersTest() {

		Flux<UserDto> flux = requester.route("user.get.all")
				.retrieveFlux(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(3)
				.verifyComplete();

	}

	private UserDto getRandomUser() {
		return requester.route("user.get.all")
			.retrieveFlux(UserDto.class)
			.next()
			.block();
	}

	@Test
	void getSingleUserTest() {

		UserDto userDto = getRandomUser();

		Mono<UserDto> mono = requester.route("user.get.{id}", userDto.getId())
				.retrieveMono(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextMatches(d -> d.getId().equals(userDto.getId()))
				.verifyComplete();

	}

	@Test
	void postUserTest() {

		UserDto userDto = new UserDto();
		userDto.setName("jeonghak");
		userDto.setBalance(100);

		Mono<UserDto> mono = requester.route("user.create")
				.data(userDto)
				.retrieveMono(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextCount(1)
				.verifyComplete();

	}

	@Test
	void putUserTest() {

		UserDto userDto = getRandomUser();
		userDto.setBalance(-100000);

		Mono<UserDto> mono = requester.route("user.update.{id}", userDto.getId())
				.data(userDto)
				.retrieveMono(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(mono)
				.expectNextMatches(d -> d.getBalance() == userDto.getBalance())
				.verifyComplete();

	}

	@Test
	void deleteUserTest() throws InterruptedException {

		UserDto userDto = getRandomUser();

		Mono<Void> mono = requester.route("user.delete.{id}", userDto.getId())
				.send();

		StepVerifier.create(mono)
				.verifyComplete();

		Thread.sleep(1000);

		Flux<UserDto> flux = requester.route("user.get.all")
				.retrieveFlux(UserDto.class)
				.doOnNext(System.out::println);

		StepVerifier.create(flux)
				.expectNextCount(2)
				.verifyComplete();

	}

	@Test
	void metadataTest() {

		MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.APPLICATION_CBOR.getString());

		UserDto dto = new UserDto();
		dto.setName("metadata");
		dto.setBalance(100);

		Mono<Void> mono = requester.route("user.operation.type")
				.metadata(OperationType.PUT, mimeType)
				.data(dto)
				.send();

		StepVerifier.create(mono)
			.verifyComplete();

	}

}
