package com.vinsguru.webfluxpatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ShippingResponse {

	private String orderId;
	private Integer quantity;
	private Status status;
	private String expectedDelivery;
	private Address address;

}
