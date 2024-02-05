package com.vinsguru.webfluxpatterns.sec06.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec06.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec06.client.ReviewClient;
import com.vinsguru.webfluxpatterns.sec06.dto.ProductAggregator;
import com.vinsguru.webfluxpatterns.sec06.dto.Product;
import com.vinsguru.webfluxpatterns.sec06.dto.Review;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {

	private final ProductClient productClient;
	private final ReviewClient reviewClient;

	public Mono<ProductAggregator> aggregate(Long id) {
		return Mono.zip(
				this.productClient.getProduct(id),
				this.reviewClient.getReview(id)
		)
		.map(t -> toDto(t.getT1(), t.getT2()));
	}

	private ProductAggregator toDto(
			Product product,
			List<Review> reviews) {
		return ProductAggregator.create(
				product.getId(),
				product.getCategory(),
				product.getDescription(),
				reviews
		);
	}

}
