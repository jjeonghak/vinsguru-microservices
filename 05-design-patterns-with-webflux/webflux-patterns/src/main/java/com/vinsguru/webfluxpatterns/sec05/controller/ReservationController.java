package com.vinsguru.webfluxpatterns.sec05.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxpatterns.sec05.dto.ReservationItemRequest;
import com.vinsguru.webfluxpatterns.sec05.dto.ReservationResponse;
import com.vinsguru.webfluxpatterns.sec05.service.ReservationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sec05")
public class ReservationController {

	private final ReservationService service;

	@PostMapping("/reserve")
	public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> requestFlux) {
		return service.reserve(requestFlux);
	}

}
