package com.vinsguru.orderservice.dto;

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
public class PurchaseOrderResponseDto {

	private Long orderId;
	private Long userId;
	private String productId;
	private Integer amount;
	private OrderStatus status;

}
