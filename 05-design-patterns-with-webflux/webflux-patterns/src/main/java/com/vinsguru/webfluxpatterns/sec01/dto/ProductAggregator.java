package com.vinsguru.webfluxpatterns.sec01.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductAggregator {

	private Long id;
	private String category;
	private String description;
	private Price price;
	private List<Review> reviews;

}
