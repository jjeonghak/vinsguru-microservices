package com.vinsguru.webfluxpatterns.sec08.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Review {

	private Long id;
	private String user;
	private Integer rating;
	private String comment;

}
