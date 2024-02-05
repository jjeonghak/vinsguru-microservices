package com.vinsguru.redisspring.geo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.redisspring.geo.dto.Restaurant;
import com.vinsguru.redisspring.geo.service.RestaurantLocatorService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geo")
public class RestaurantController {

	private final RestaurantLocatorService service;

	@GetMapping("/{zip}")
	public Flux<Restaurant> getRestaurants(@PathVariable("zip") String zip) {
		return service.getRestaurants(zip);
	}

}
