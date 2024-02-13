package com.vinsguru.graphqlplayground.sec15.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerNotFound {

	private Long id;
	private final String message = "Not found user";

}
