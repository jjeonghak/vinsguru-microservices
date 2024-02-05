package com.vinsguru.redisspring.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.redisspring.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

	private final WeatherService service;

	@GetMapping("/{zip}")
	public Mono<Integer> getWeather(@PathVariable("zip") int zip) {
		return Mono.fromSupplier(() -> service.getInfo(zip));
	}

}
