package com.vinsguru.webfluxpatterns.sec06.dto;

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
	private List<Review> reviews;

}
