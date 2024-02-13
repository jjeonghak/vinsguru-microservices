package com.vinsguru.graphqlplayground.sec16.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CustomerEvent {

	private Long id;
	private Action action;

}
