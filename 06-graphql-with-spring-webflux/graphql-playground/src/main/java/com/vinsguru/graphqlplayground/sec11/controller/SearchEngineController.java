package com.vinsguru.graphqlplayground.sec11.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec11.dto.Book;
import com.vinsguru.graphqlplayground.sec11.dto.Electronics;
import com.vinsguru.graphqlplayground.sec11.dto.Fruit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class SearchEngineController {

	private final List<Object> list = List.of(
			Fruit.create("banana", LocalDate.now().plusDays(3)),
			Fruit.create("apple", LocalDate.now().plusDays(5)),
			Electronics.create("mac-book", 600, "APPLE"),
			Electronics.create("phone-GN", 400, "SAMSUNG"),
			Book.create("java", "venkat")
	);

	@QueryMapping("search")
	public Flux<Object> search() {
		return Mono.fromSupplier(() -> (List<Object>) new ArrayList(list))
				.doOnNext(Collections::shuffle)
				.flatMapIterable(Function.identity())
				.take(ThreadLocalRandom.current().nextInt(0, list.size()));
	}

}
