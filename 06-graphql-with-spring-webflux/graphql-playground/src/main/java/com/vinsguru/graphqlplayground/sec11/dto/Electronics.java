package com.vinsguru.graphqlplayground.sec11.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Electronics {

	private final UUID id = UUID.randomUUID();
	private String description;
	private Integer price;
	private String brand;

}
