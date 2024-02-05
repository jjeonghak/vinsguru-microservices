package com.vinsguru.productservice.dto;

import com.vinsguru.productservice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private String id;
	private String description;
	private Integer price;

	public static ProductDto of(String description, Integer price) {
		return ProductDto.builder()
				.description(description)
				.price(price)
				.build();
	}

	public static ProductDto from(Product product) {
		return ProductDto.builder()
				.id(product.getId())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}

}
