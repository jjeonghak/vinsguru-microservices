package com.vinsguru.webfluxdemo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.exceptions.InputValidationException;
import com.vinsguru.webfluxdemo.services.ReactiveMathService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reactive/math")
public class ReactiveMathValidationController {

	private final ReactiveMathService service;

	@GetMapping(value = "/square/throw/{input}")
	public Mono<Response> findSquare(@PathVariable(value = "input") int input) {
		if (input < 10 || input > 20) {
			throw new InputValidationException(input);
		}
		return service.findSquare(input);
	}

	@GetMapping(value = "/square/mono/error/{input}")
	public Mono<Response> monoError(@PathVariable(value = "input") int input) {
		return Mono.just(input)
				.handle(((integer, sink) -> {
						if (integer >= 10 && integer <= 20) {
							sink.next(integer);
							sink.complete();
						} else {
							sink.error(new InputValidationException(integer));
						}
				}))
				.cast(Integer.class)
				.flatMap(service::findSquare);
	}

	@GetMapping(value = "/square/assignment/{input}")
	public Mono<ResponseEntity<Response>> assignment(@PathVariable(value = "input") int input) {
		return Mono.just(input)
				.filter(i -> i >= 10 && i <= 20)
				.doOnDiscard(Integer.class, i -> System.out.println("ReactiveMathValidationController: It was bad input by " + input))
				.flatMap(service::findSquare)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

}
