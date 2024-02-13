package com.vinsguru.graphqlplayground.sec09.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec09.dto.AllTypes;
import com.vinsguru.graphqlplayground.sec09.dto.Car;
import com.vinsguru.graphqlplayground.sec09.dto.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ScalarController {

	private final AllTypes allTypes = AllTypes.create(
			UUID.randomUUID(),
			10,
			10.12f,
			"atlanta",
			false,
			1200L,
			Byte.valueOf("12"),
			Short.valueOf("100"),
			BigDecimal.ONE,
			BigInteger.ONE,
			LocalDate.now(),
			LocalTime.now(),
			OffsetDateTime.now(),
			Car.BMW
	);

	@QueryMapping("get")
	public Mono<AllTypes> get() {
		return Mono.just(allTypes);
	}

	@QueryMapping("products")
	public Flux<Product> products() {
		return Flux.just(
				Product.create("banana", Map.of(
						"expiry date", "01/01/2025",
						"color", "yellow"
				)),
				Product.create("mac", Map.of(
						"CPU", "8",
						"RAM", "32G"
				))
		);
	}

}
