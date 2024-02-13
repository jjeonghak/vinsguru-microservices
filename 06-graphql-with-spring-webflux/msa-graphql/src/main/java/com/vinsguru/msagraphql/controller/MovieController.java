package com.vinsguru.msagraphql.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.msagraphql.client.MovieClient;
import com.vinsguru.msagraphql.dto.Customer;
import com.vinsguru.msagraphql.dto.Genre;
import com.vinsguru.msagraphql.dto.Movie;
import com.vinsguru.msagraphql.dto.WatchListResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class MovieController {

	private final MovieClient client;

	@SchemaMapping(typeName = "UserProfile")
	public Flux<Movie> watchList(Customer customer) {
		return this.client.moviesByIds(customer.getWatchList());
	}

	@SchemaMapping(typeName = "UserProfile")
	public Flux<Movie> recommended(Customer customer) {
		return this.client.moviesByGenre(customer.getFavoriteGenre());
	}

	@SchemaMapping(typeName = "WatchListResponse")
	public Flux<Movie> watchList(WatchListResponse response) {
		return this.client.moviesByIds(response.getWatchList());
	}

	@QueryMapping("movieDetails")
	public Mono<Movie> movieDetails(@Argument("id") Long id) {
		return this.client.moviesByIds(List.of(id)).next();
	}

	@QueryMapping("moviesByGenre")
	public Flux<Movie> moviesByGenre(@Argument("genre") Genre genre) {
		return this.client.moviesByGenre(genre);
	}

}
