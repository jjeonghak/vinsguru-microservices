package com.vinsguru.webfluxpatterns.sec05.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ReservationResponse {

	private String reservationId;
	private Integer price;
	private List<ReservationItemResponse> items;

}
