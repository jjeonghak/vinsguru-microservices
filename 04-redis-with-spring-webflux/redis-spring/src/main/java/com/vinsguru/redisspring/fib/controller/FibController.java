package com.vinsguru.redisspring.fib.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.redisspring.fib.service.FibService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fib")
public class FibController {

	private final FibService service;

	@GetMapping("/{index}")
	public Mono<Integer> getFib(@PathVariable("index") int index) {
		return Mono.fromSupplier(() -> this.service.getFib(index));
	}

	@GetMapping("/{index}/{name}")
	public Mono<Integer> getFibName(
			@PathVariable("index") int index,
			@PathVariable("name") String name) {
		return Mono.fromSupplier(() -> this.service.getFib(index, name));
	}

	@GetMapping("/clear/{index}")
	public Mono<Void> clearCache(@PathVariable("index") int index) {
		return Mono.fromRunnable(() -> this.service.clearCache(index));
	}

}
