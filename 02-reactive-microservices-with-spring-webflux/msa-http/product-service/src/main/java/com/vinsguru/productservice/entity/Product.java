package com.vinsguru.productservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vinsguru.productservice.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	private String id;
	private String description;
	private Integer price;

	public static Product persist(ProductDto dto) {
		return Product.builder()
				.id(dto.getId())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.build();
	}

	public Product update(ProductDto dto) {
		this.id = dto.getId();
		this.description = dto.getDescription();
		this.price = dto.getPrice();
		return this;
	}

}
