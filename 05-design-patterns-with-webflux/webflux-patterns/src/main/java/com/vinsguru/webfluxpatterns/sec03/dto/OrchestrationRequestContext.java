package com.vinsguru.webfluxpatterns.sec03.dto;

import java.util.UUID;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrchestrationRequestContext {

	private final String orderId = UUID.randomUUID().toString();
	private OrderRequest orderRequest;
	private Integer productPrice;
	private PaymentRequest paymentRequest;
	private PaymentResponse paymentResponse;
	private InventoryRequest inventoryRequest;
	private InventoryResponse inventoryResponse;
	private ShippingRequest shippingRequest;
	private ShippingResponse shippingResponse;
	private Status status;

	public OrchestrationRequestContext(OrderRequest orderRequest) {
		this.orderRequest = orderRequest;
	}

}
