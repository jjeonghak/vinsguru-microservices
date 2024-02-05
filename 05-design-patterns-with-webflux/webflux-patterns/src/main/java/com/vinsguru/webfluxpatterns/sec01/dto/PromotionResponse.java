package com.vinsguru.webfluxpatterns.sec01.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PromotionResponse {

	private Long id;
	private String type;
	private Double discount;
	private LocalDate endDate;

}
