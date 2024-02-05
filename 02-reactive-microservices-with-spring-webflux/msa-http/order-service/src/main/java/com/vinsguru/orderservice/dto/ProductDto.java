package com.vinsguru.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private String id;
	private String description;
	private Integer price;

	public static ProductDto of(String id, String description, Integer price) {
		return ProductDto.builder()
				.id(id)
				.description(description)
				.price(price)
				.build();
	}

}
