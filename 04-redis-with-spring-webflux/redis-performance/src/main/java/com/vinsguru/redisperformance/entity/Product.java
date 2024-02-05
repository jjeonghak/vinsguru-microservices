package com.vinsguru.redisperformance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	private Long id;
	private String description;
	private double price;

}
