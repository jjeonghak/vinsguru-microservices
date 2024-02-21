package com.vinsguru.productservice.service;

import org.springframework.stereotype.Service;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.event.ProductViewEvent;
import com.vinsguru.productservice.repository.ProductRepository;
import com.vinsguru.productservice.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository repository;
	private final ProductViewEventProducer producer;

	public Mono<ProductDto> getProduct(long id) {
		return this.repository.findById(id)
				.doOnNext(e -> this.producer.emitEvent(new ProductViewEvent(e.getId())))
				.map(EntityDtoUtil::toDto);
	}

}
