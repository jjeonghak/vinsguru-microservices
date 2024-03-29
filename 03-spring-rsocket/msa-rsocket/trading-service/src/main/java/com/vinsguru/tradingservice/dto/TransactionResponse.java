package com.vinsguru.tradingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

	private String userId;
	private int amount;
	private TransactionType type;
	private TransactionStatus status;

}
