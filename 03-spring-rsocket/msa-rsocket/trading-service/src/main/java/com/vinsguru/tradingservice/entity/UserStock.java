package com.vinsguru.tradingservice.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserStock {

	@Id
	private String id;
	private String userId;
	private String stockSymbol;
	private Integer quantity;

}
