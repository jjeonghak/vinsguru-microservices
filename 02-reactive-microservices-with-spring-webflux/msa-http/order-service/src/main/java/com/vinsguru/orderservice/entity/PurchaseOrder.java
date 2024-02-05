package com.vinsguru.orderservice.entity;

import com.vinsguru.orderservice.dto.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class PurchaseOrder {

	@Id @GeneratedValue
	private Long id;
	private String productId;
	private Long userId;
	private Integer amount;
	private OrderStatus status;

}
