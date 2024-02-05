package com.vinsguru.webfluxpatterns.sec04.service;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.OrderRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.OrderResponse;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import com.vinsguru.webfluxpatterns.sec04.util.DebugUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrchestratorService {

	private final OrderFulfilmentService fulfilmentService;
	private final OrderCancellationService cancellationService;

	public Mono<OrderResponse> placeOrder(Mono<OrderRequest> requestMono) {
		return requestMono
				.map(OrchestrationRequestContext::new)
				.flatMap(fulfilmentService::placeOrder)
				.doOnNext(this::doOrderPostProcessing)
				.doOnNext(ctx -> DebugUtil.print("OrchestratorService", ctx))
				.map(this::toOrderResponse);
	}

	private void doOrderPostProcessing(OrchestrationRequestContext ctx) {
		if (ctx.getStatus().equals(Status.FAILED)) {
			this.cancellationService.cancelOrder(ctx);
		}
	}

	private OrderResponse toOrderResponse(OrchestrationRequestContext ctx) {
		var isSuccess = ctx.getStatus().equals(Status.SUCCESS);
		var address = isSuccess ? ctx.getShippingResponse().getAddress() : null;
		var deliveryDate = isSuccess ? ctx.getShippingResponse().getExpectedDelivery() : null;

		return OrderResponse.create(
				ctx.getOrderRequest().getUserId(),
				ctx.getOrderRequest().getProductId(),
				ctx.getOrderId(),
				ctx.getStatus(),
				address,
				deliveryDate
		);
	}

}
