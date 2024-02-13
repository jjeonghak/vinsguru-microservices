package com.vinsguru.graphqlplayground.sec10.controller;

import java.time.LocalDate;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec10.dto.Book;
import com.vinsguru.graphqlplayground.sec10.dto.Electronics;
import com.vinsguru.graphqlplayground.sec10.dto.Fruit;

import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@QueryMapping("products")
	public Flux<Object> products() {
		return Flux.just(
				Fruit.create("banana", 1, LocalDate.now().plusDays(3)),
				Fruit.create("apple", 2, LocalDate.now().plusDays(5)),
				Electronics.create("mac-book", 600, "APPLE"),
				Electronics.create("phone-GN", 400, "SAMSUNG"),
				Book.create("java", 40, "venkat")
		);
	}

}
