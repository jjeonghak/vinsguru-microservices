package com.vinsguru.msagraphql.dto;

import lombok.Data;

@Data
public class Movie {

	private Long id;
	private String title;
	private Integer releaseYear;
	private Double rating;
	private Genre genre;

}
