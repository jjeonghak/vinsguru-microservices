package com.vinsguru.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.userservice.dto.TransactionRequestDto;
import com.vinsguru.userservice.dto.TransactionResponseDto;
import com.vinsguru.userservice.service.TransactionService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user/transaction")
public class UserTransactionController {

	private final TransactionService service;

	@PostMapping
	public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> dtoMono) {
		return dtoMono.flatMap(service::createTransaction);
	}

	@GetMapping
	public Flux<TransactionResponseDto> getByUserId(@RequestParam("userId") long userId) {
		return service.getByUserId(userId);
	}

}
