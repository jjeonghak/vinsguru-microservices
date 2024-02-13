package com.vinsguru.graphqlplayground.sec02.dto;

import lombok.Data;

@Data
public class AgeRangeFilter {

	private Integer min;
	private Integer max;

	public boolean isBetween(int age) {
		return min <= age && age <= max;
	}

}
