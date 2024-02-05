package com.vinsguru.redisspring.city.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.redisspring.city.dto.City;
import com.vinsguru.redisspring.city.service.CityService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityController {

	private final CityService service;

	@GetMapping("/{zipcode}")
	public Mono<City> getCity(@PathVariable("zipcode") String zipCode) {
		return service.getCity(zipCode);
	}

}
