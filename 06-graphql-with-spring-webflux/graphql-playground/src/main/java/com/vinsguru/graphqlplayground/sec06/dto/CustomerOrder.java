package com.vinsguru.graphqlplayground.sec06.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerOrder {

	private String id;
	private String description;

}
