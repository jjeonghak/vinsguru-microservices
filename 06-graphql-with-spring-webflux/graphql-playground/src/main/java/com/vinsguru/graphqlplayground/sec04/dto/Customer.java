package com.vinsguru.graphqlplayground.sec04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Customer {

	private Long id;
	private String name;
	private Integer age;
	private String city;

}
