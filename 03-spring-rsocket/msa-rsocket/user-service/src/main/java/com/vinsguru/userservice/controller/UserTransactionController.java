package com.vinsguru.userservice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.userservice.dto.TransactionRequest;
import com.vinsguru.userservice.dto.TransactionResponse;
import com.vinsguru.userservice.service.UserTransactionService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@MessageMapping("user")
public class UserTransactionController {

	private final UserTransactionService service;

	@MessageMapping("transaction")
	public Mono<TransactionResponse> doTransaction(Mono<TransactionRequest> transactionRequestMono) {
		return transactionRequestMono.flatMap(service::doTransaction);
	}

}
