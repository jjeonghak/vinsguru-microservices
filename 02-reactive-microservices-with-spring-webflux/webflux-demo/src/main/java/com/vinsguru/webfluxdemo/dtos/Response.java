package com.vinsguru.webfluxdemo.dtos;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Response {

	private int output;
	private LocalDate date;

	public Response(int output, LocalDate date) {
		this.output = output;
		this.date = date;
	}

}
