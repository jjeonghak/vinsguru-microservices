package com.vinsguru.webfluxpatterns.sec05.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CarReservationResponse {

	private String reservationId;
	private String city;
	private LocalDate pickup;
	private LocalDate drop;
	private String category;
	private Integer price;

}
