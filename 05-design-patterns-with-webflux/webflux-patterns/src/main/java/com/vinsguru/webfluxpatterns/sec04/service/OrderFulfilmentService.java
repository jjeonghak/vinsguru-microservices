package com.vinsguru.webfluxpatterns.sec04.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec04.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.vinsguru.webfluxpatterns.sec04.dto.Product;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;
import com.vinsguru.webfluxpatterns.sec04.util.OrchestrationUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderFulfilmentService {

	private final ProductClient productClient;
	private final PaymentOrchestrator paymentOrchestrator;
	private final InventoryOrchestrator inventoryOrchestrator;
	private final ShippingOrchestrator shippingOrchestrator;

	public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
		return this.getProduct(ctx)
				.doOnNext(OrchestrationUtil::buildPaymentRequest)
				.flatMap(this.paymentOrchestrator::create)
				.doOnNext(OrchestrationUtil::buildInventoryRequest)
				.flatMap(this.inventoryOrchestrator::create)
				.doOnNext(OrchestrationUtil::buildShippingRequest)
				.flatMap(this.shippingOrchestrator::create)
				.doOnNext(c -> c.setStatus(Status.SUCCESS))
				.doOnError(ex -> ctx.setStatus(Status.FAILED))
				.onErrorReturn(ctx);
	}

	private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx) {
		return this.productClient
				.getProduct(ctx.getOrderRequest().getProductId())
				.map(Product::getPrice)
				.doOnNext(ctx::setProductPrice)
				.map(i -> ctx);
	}

}
