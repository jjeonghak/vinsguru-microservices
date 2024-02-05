package com.vinsguru.webfluxpatterns.sec01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxpatterns.sec01.client.ProductClient;
import com.vinsguru.webfluxpatterns.sec01.client.PromotionClient;
import com.vinsguru.webfluxpatterns.sec01.client.ReviewClient;
import com.vinsguru.webfluxpatterns.sec01.dto.Price;
import com.vinsguru.webfluxpatterns.sec01.dto.ProductAggregator;
import com.vinsguru.webfluxpatterns.sec01.dto.ProductResponse;
import com.vinsguru.webfluxpatterns.sec01.dto.PromotionResponse;
import com.vinsguru.webfluxpatterns.sec01.dto.Review;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {

	private final ProductClient productClient;
	private final PromotionClient promotionClient;
	private final ReviewClient reviewClient;

	public Mono<ProductAggregator> aggregate(Long id) {
		return Mono.zip(
				this.productClient.getProduct(id),
				this.promotionClient.getPromotion(id),
				this.reviewClient.getReview(id)
		)
		.map(t -> toDto(t.getT1(), t.getT2(), t.getT3()));
	}

	private ProductAggregator toDto(
			ProductResponse product,
			PromotionResponse promotion,
			List<Review> reviews) {
		var price = new Price();
		var amountSaved = product.getPrice() * promotion.getDiscount() / 100;
		var discountedPrice = product.getPrice() - amountSaved;

		price.setListPrice(product.getPrice());
		price.setAmountSaved(amountSaved);
		price.setDiscountPrice(discountedPrice);
		price.setDiscount(promotion.getDiscount());
		price.setEndDate(promotion.getEndDate());

		return ProductAggregator.create(
				product.getId(),
				product.getCategory(),
				product.getDescription(),
				price,
				reviews
		);
	}

}
