package com.vinsguru.graphqlplayground.sec16.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerNotFound implements CustomerResponse {

	private Long id;
	private String message;

}
