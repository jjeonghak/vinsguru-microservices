package com.vinsguru.userservice.service;

import org.springframework.stereotype.Service;

import com.vinsguru.userservice.dto.TransactionRequestDto;
import com.vinsguru.userservice.dto.TransactionResponseDto;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.entity.UserTransaction;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.repository.UserTransactionRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final UserRepository userRepository;
	private final UserTransactionRepository transactionRepository;

	public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto dto) {
		return userRepository.updateUserBalance(dto.getUserId(), dto.getAmount())
				.filter(Boolean::booleanValue)
				.map(b -> UserTransaction.persist(dto))
				.flatMap(transactionRepository::save)
				.map(t -> TransactionResponseDto.of(dto, TransactionStatus.APPROVED))
				.defaultIfEmpty(TransactionResponseDto.of(dto, TransactionStatus.DECLINED));
	}

	public Flux<TransactionResponseDto> getByUserId(long userId) {
		return transactionRepository.findByUserId(userId)
				.map(t -> TransactionResponseDto.of(t, TransactionStatus.APPROVED))
				.cast(TransactionResponseDto.class);
	}

}
