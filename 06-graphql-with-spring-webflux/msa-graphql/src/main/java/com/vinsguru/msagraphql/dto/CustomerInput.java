package com.vinsguru.msagraphql.dto;

import lombok.Data;

@Data
public class CustomerInput {

	private Long id;
	private String name;
	private Genre favoriteGenre;

}
