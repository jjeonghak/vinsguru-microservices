package com.vinsguru.graphqlplayground.sec09.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Product {

	private String name;
	private Map<String, String> attributes;

}
