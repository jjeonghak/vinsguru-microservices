package com.vinsguru.webfluxpatterns.sec01.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Price {

	private Integer listPrice;
	private Double discount;
	private Double discountPrice;
	private Double amountSaved;
	private LocalDate endDate;

}
