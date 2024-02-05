package com.vinsguru.webfluxpatterns.sec02.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FlightResult {

	private String airline;
	private String from;
	private String to;
	private Double price;
	private LocalDate date;

}
