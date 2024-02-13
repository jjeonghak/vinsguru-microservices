package com.vinsguru.msagraphql.dto;

import java.util.List;

import lombok.Data;

@Data
public class Customer {

	private Long id;
	private String name;
	private Genre favoriteGenre;
	private List<Long> watchList;

}
