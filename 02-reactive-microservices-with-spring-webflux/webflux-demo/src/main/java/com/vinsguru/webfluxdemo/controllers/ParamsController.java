package com.vinsguru.webfluxdemo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/jobs")
public class ParamsController {

	@GetMapping(value = "/search")
	public Flux<Integer> searchJob(
			@RequestParam("pageSize") int pageSize,
			@RequestParam("pageNumber") int pageNumber) {
		return Flux.just(pageSize, pageNumber);
	}

}
