package com.vinsguru.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentResponse {

	private String paymentId;
	private Long userId;
	private String name;
	private Integer balance;
	private Status status;

}
