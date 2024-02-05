package com.vinsguru.springrsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartResponseDto {

	private int input;
	private int output;

	private String getFormat(int value) {
		return "%3s|%" + value + "s";
	}

	@Override
	public String toString() {
		String graphFormat = getFormat(this.output);
		return String.format(graphFormat, this.input, "X");
	}

}
