package com.vinsguru.graphqlplayground.sec13.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Customer {

	@Id
	private Long id;
	private String name;
	private Integer age;
	private String city;

}
