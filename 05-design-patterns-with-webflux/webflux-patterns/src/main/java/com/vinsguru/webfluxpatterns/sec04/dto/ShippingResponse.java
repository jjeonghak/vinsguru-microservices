package com.vinsguru.webfluxpatterns.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ShippingResponse {

	private String shippingId;
	private Integer quantity;
	private Status status;
	private String expectedDelivery;
	private Address address;

}
