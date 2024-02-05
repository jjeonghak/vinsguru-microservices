package com.vinsguru.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

	private final UserService service;

	@GetMapping("/all")
	public Flux<UserDto> all() {
		return service.all();
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable("id") Long id) {
		return service.getUserById(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@PostMapping
	public Mono<UserDto> createUser(@RequestBody Mono<UserDto> dto) {
		return service.createUser(dto);
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<UserDto>> updateUser(
			@PathVariable("id") Long id,
			@RequestBody Mono<UserDto> dto) {
		return service.updateUser(id, dto)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteUser(@PathVariable("id") Long id) {
		return service.deleteUser(id);
	}

}
