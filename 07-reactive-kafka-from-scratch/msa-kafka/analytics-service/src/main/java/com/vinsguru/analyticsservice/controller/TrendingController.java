package com.vinsguru.analyticsservice.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.analyticsservice.dto.ProductTrendingDto;
import com.vinsguru.analyticsservice.service.ProductTrendingBroadcastService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trending")
public class TrendingController {

	private final ProductTrendingBroadcastService broadcastService;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<List<ProductTrendingDto>> trending() {
		return this.broadcastService.getTrends();
	}

}
