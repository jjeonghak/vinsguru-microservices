package com.vinsguru.webfluxpatterns.sec09.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {

	private Long id;
	private String category;
	private String description;
	private Integer price;

}
