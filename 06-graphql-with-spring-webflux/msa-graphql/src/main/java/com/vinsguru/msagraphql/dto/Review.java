package com.vinsguru.msagraphql.dto;

import lombok.Data;

@Data
public class Review {

	private Long id;
	private String username;
	private Integer rating;
	private String comment;

}
