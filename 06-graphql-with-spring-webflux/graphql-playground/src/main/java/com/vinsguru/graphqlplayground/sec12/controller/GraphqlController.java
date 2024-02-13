package com.vinsguru.graphqlplayground.sec12.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class GraphqlController {

	@QueryMapping("basicWithArgument")
	public Mono<String> basicWithArgument(@Argument("arg") String value) {
		return Mono.fromSupplier(() -> "basic with " + value);
	}

}
