package com.vinsguru.graphqlplayground.sec10.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Fruit {

	private final UUID id = UUID.randomUUID();
	private String description;
	private Integer price;
	private LocalDate expiryDate;

}
