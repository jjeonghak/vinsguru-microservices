package com.vinsguru.graphqlplayground.sec11.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Fruit {

	private String description;
	private LocalDate expiryDate;

}
