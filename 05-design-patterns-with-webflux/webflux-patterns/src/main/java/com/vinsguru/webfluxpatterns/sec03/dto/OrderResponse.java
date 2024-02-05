package com.vinsguru.webfluxpatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderResponse {

	private Long userId;
	private Long productId;
	private String orderId;
	private Status status;
	private Address shippingAddress;
	private String expectedDelivery;

}
