package com.vinsguru.webfluxpatterns.sec02.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxpatterns.sec02.dto.FlightResult;
import com.vinsguru.webfluxpatterns.sec02.service.FlightSearchService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sec02")
public class FlightController {

	private final FlightSearchService service;

	@GetMapping(value = "/flights/{from}/{to}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<FlightResult> getFlights(
			@PathVariable("from") String from,
			@PathVariable("to") String to) {
		return service.getFlights(from, to);
	}

}
