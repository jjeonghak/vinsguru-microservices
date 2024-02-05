package com.vinsguru.userservice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.vinsguru.userservice.dto.TransactionRequestDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserTransaction {

	@Id
	private Long id;
	private Long userId;
	private Integer amount;
	private LocalDateTime transactionAt;

	public static UserTransaction persist(TransactionRequestDto dto) {
		return UserTransaction.builder()
				.userId(dto.getUserId())
				.amount(dto.getAmount())
				.transactionAt(LocalDateTime.now())
				.build();
	}

}
