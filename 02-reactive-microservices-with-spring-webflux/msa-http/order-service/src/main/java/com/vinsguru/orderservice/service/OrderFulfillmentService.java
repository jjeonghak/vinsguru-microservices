package com.vinsguru.orderservice.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.vinsguru.orderservice.client.ProductClient;
import com.vinsguru.orderservice.client.UserClient;
import com.vinsguru.orderservice.dto.PurchaseOrderRequestDto;
import com.vinsguru.orderservice.dto.PurchaseOrderResponseDto;
import com.vinsguru.orderservice.dto.RequestContext;
import com.vinsguru.orderservice.repository.PurchaseOrderRepository;
import com.vinsguru.orderservice.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

	private final ProductClient productClient;
	private final UserClient userClient;

	private final PurchaseOrderRepository orderRepository;

	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
		return requestDtoMono.map(RequestContext::new)
				.flatMap(this::productRequestResponse)
				.doOnNext(EntityDtoUtil::setTransactionRequestDto)
				.flatMap(this::userRequestResponse)
				.map(EntityDtoUtil::getPurchaseOrder)
				.map(orderRepository::save) //blocking
				.map(EntityDtoUtil::getPurchaseOrderResponseDto)
				.subscribeOn(Schedulers.boundedElastic());
	}

	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
				.doOnNext(rc::setProductDto)
				.retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
				.thenReturn(rc);
	}

	private Mono<RequestContext> userRequestResponse(RequestContext rc) {
		return userClient.authorizeTransaction(rc.getTransactionRequestDto())
				.doOnNext(rc::setTransactionResponseDto)
				.thenReturn(rc);
	}


}
