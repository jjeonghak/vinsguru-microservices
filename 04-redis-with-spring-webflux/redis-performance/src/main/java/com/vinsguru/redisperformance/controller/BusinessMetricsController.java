package com.vinsguru.redisperformance.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.redisperformance.service.BusinessMetricsService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/metrics")
public class BusinessMetricsController {

	private final BusinessMetricsService service;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Map<Long, Double>> getMetrics() {
		return service.topProducts(3)
				.repeatWhen(l -> Flux.interval(Duration.ofSeconds(3)));
	}

}
