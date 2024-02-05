package com.vinsguru.userservice.service;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Service;

import com.vinsguru.userservice.dto.TransactionRequest;
import com.vinsguru.userservice.dto.TransactionResponse;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.dto.TransactionType;
import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTransactionService {

	private final UserRepository userRepository;

	public Mono<TransactionResponse> doTransaction(TransactionRequest request) {
		UnaryOperator<Mono<User>> operation = request.getType().equals(TransactionType.CREDIT) ? credit(request) : debit(request);
		return userRepository.findById(request.getUserId())
				.transform(operation)
				.flatMap(userRepository::save)
				.map(s -> EntityDtoUtil.toResponse(request, TransactionStatus.COMPLETED))
				.defaultIfEmpty(EntityDtoUtil.toResponse(request, TransactionStatus.FAILED));
	}

	private UnaryOperator<Mono<User>> credit(TransactionRequest request) {
		return userMono -> userMono
				.doOnNext(u -> u.setBalance(u.getBalance() + request.getAmount()));
	}

	private UnaryOperator<Mono<User>> debit(TransactionRequest request) {
		return userMono -> userMono
				.filter(u -> u.getBalance() >= request.getAmount())
				.doOnNext(u -> u.setBalance(u.getBalance() - request.getAmount()));
	}

}
