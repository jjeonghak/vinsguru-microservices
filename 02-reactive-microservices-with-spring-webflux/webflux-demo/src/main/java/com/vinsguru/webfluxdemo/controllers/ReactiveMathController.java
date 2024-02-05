package com.vinsguru.webfluxdemo.controllers;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxdemo.dtos.MultiplyRequestDto;
import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.services.ReactiveMathService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reactive/math")
public class ReactiveMathController {

	private final ReactiveMathService service;

	@GetMapping(value = "/square/{input}")
	public Mono<Response> findSquare(@PathVariable(value = "input") int input) {
		return service.findSquare(input);
	}

	@GetMapping(value = "/table/{input}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Response> multiplicationTable(@PathVariable(value = "input") int input) {
		return service.multiplicationTable(input);
	}

	@GetMapping(value = "/table/stream/{input}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> multiplicationTableStream(@PathVariable(value = "input") int input) {
		return service.multiplicationTable(input);
	}

	@PostMapping(value = "multiply")
	public Mono<Response> multiply(
			@RequestBody Mono<MultiplyRequestDto> request,
			@RequestHeader Map<String, String> headers) {
		System.out.println(headers);
		return service.multiply(request);
	}


}
