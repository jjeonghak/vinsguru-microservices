package com.vinsguru.orderservice.service;

import org.springframework.stereotype.Service;

import com.vinsguru.orderservice.dto.PurchaseOrderResponseDto;
import com.vinsguru.orderservice.repository.PurchaseOrderRepository;
import com.vinsguru.orderservice.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

	private final PurchaseOrderRepository orderRepository;

	public Flux<PurchaseOrderResponseDto> getProductsByUserId(long userId) {
		return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream()) //blocking
				.map(EntityDtoUtil::getPurchaseOrderResponseDto)
				.subscribeOn(Schedulers.boundedElastic());
	}

}
