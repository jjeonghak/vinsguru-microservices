package com.vinsguru.analyticsservice.service;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.vinsguru.analyticsservice.dto.ProductTrendingDto;
import com.vinsguru.analyticsservice.repository.ProductViewRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProductTrendingBroadcastService {

	private final ProductViewRepository repository;
	private final ProductViewEventConsumer consumer;
	private Flux<List<ProductTrendingDto>> trends;

	@PostConstruct
	private void init() {
		this.trends = this.repository.findTop5ByOrderByCountDesc()
				.map(pvc -> new ProductTrendingDto(pvc.getId(), pvc.getCount()))
				.collectList()
				.filter(Predicate.not(List::isEmpty))
				// .repeatWhen(l -> l.delayElements(Duration.ofSeconds(3)))
				.repeatWhen(l -> consumer.companionFlux())
				.distinctUntilChanged()
				.cache(1);
	}

	public Flux<List<ProductTrendingDto>> getTrends() {
		return this.trends;
	}

}
