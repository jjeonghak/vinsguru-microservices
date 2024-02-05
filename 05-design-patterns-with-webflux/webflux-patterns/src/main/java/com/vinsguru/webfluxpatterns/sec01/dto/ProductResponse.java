package com.vinsguru.webfluxpatterns.sec01.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductResponse {

	private Long id;
	private String category;
	private String description;
	private Integer price;

}
