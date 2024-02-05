package com.vinsguru.orderservice.util;

import org.springframework.beans.BeanUtils;

import com.vinsguru.orderservice.dto.OrderStatus;
import com.vinsguru.orderservice.dto.PurchaseOrderResponseDto;
import com.vinsguru.orderservice.dto.RequestContext;
import com.vinsguru.orderservice.dto.TransactionRequestDto;
import com.vinsguru.orderservice.dto.TransactionStatus;
import com.vinsguru.orderservice.entity.PurchaseOrder;

public class EntityDtoUtil {

	public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
		PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
		BeanUtils.copyProperties(purchaseOrder, dto);
		dto.setOrderId(purchaseOrder.getId());
		return dto;
	}

	public static void setTransactionRequestDto(RequestContext requestContext) {
		TransactionRequestDto dto = new TransactionRequestDto();
		dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
		dto.setAmount(requestContext.getProductDto().getPrice());
		requestContext.setTransactionRequestDto(dto);
	}

	public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
		purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
		purchaseOrder.setAmount(requestContext.getProductDto().getPrice());

		TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
		OrderStatus orderStatus = status.equals(TransactionStatus.APPROVED) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
		purchaseOrder.setStatus(orderStatus);
		return purchaseOrder;
	}

}
