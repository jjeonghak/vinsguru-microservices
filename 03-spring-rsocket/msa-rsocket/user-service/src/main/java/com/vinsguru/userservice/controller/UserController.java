package com.vinsguru.userservice.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.userservice.dto.OperationType;
import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@MessageMapping("user")
public class UserController {

	private final UserService service;

	@MessageMapping("get.all")
	public Flux<UserDto> allUsers() {
		return service.getAllUsers();
	}

	@MessageMapping("get.{id}")
	public Mono<UserDto> getUserById(@DestinationVariable("id") String id) {
		return service.getUserById(id);
	}

	@MessageMapping("create")
	public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
		return service.createUser(userDtoMono);
	}

	@MessageMapping("update.{id}")
	public Mono<UserDto> updateUser(@DestinationVariable("id") String id, Mono<UserDto> userDtoMono) {
		return service.updateUser(id, userDtoMono);
	}

	@MessageMapping("delete.{id}")
	public Mono<Void> deleteUser(@DestinationVariable("id") String id) {
		return service.deleteUser(id);
	}

	@MessageMapping("operation.type")
	public Mono<Void> metadataOperationType(
			@Header("operation-type") OperationType operationType,
			Mono<UserDto> userDtoMono) {
		System.out.println("UserController: operation type: " + operationType);
		userDtoMono.doOnNext(d -> System.out.println("UserController: user dto mono is " + d)).subscribe();
		return Mono.empty();
	}

}
