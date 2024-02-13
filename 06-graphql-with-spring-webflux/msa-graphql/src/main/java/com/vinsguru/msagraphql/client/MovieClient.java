package com.vinsguru.msagraphql.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.msagraphql.dto.Genre;
import com.vinsguru.msagraphql.dto.Movie;

import reactor.core.publisher.Flux;

@Service
public class MovieClient {

	private final WebClient client;

	public MovieClient(@Value("${service.url.movie}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Flux<Movie> moviesByGenre(Genre genre) {
		return this.client.get()
				.uri("/{genre}/recommended", genre)
				.retrieve()
				.bodyToFlux(Movie.class);
	}

	public Flux<Movie> moviesByIds(List<Long> ids) {
		return ids.isEmpty() ? Flux.empty() : getMoviesById(ids);
	}

	private Flux<Movie> getMoviesById(List<Long> ids) {
		return this.client.get()
				.uri(b -> b.queryParam("ids", ids).build())
				.retrieve()
				.bodyToFlux(Movie.class);
	}

}
