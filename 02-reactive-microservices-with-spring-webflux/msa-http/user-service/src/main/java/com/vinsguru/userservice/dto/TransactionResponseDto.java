package com.vinsguru.userservice.dto;

import com.vinsguru.userservice.entity.UserTransaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

	private Long userId;
	private Integer amount;
	private TransactionStatus status;

	public static TransactionResponseDto of(TransactionRequestDto transaction, TransactionStatus status) {
		return TransactionResponseDto.builder()
				.userId(transaction.getUserId())
				.amount(transaction.getAmount())
				.status(status)
				.build();
	}

	public static TransactionResponseDto of(UserTransaction transaction, TransactionStatus status) {
		return TransactionResponseDto.builder()
				.userId(transaction.getUserId())
				.amount(transaction.getAmount())
				.status(status)
				.build();
	}

}
