package com.vinsguru.graphqlplayground.sec05.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Account {

	private String id;
	private Integer amount;
	private String accountType;

}
