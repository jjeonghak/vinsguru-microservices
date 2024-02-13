package com.vinsguru.msagraphql.controller;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.msagraphql.client.ReviewClient;
import com.vinsguru.msagraphql.dto.Movie;
import com.vinsguru.msagraphql.dto.Review;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewClient client;

	@SchemaMapping(typeName = "MovieDetails")
	public Flux<Review> reviews(Movie movie) {
		return this.client.reviews(movie.getId());
	}

}
