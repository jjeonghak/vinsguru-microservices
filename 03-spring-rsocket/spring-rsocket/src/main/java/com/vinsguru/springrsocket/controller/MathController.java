package com.vinsguru.springrsocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.springrsocket.dto.ChartResponseDto;
import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import com.vinsguru.springrsocket.dto.ComputationResponseDto;
import com.vinsguru.springrsocket.service.MathService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class MathController {

	private final MathService service;

	@MessageMapping("math.service.print")
	public Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono) {
		return service.print(requestDtoMono);
	}

	@MessageMapping("math.service.square")
	public Mono<ComputationResponseDto> findSquare(Mono<ComputationRequestDto> requestDtoMono) {
		return service.findSquare(requestDtoMono);
	}

	@MessageMapping("math.service.table")
	public Flux<ComputationResponseDto> tableStream(Mono<ComputationRequestDto> requestDtoMono) {
		return requestDtoMono.flatMapMany(service::tableStream);
	}

	@MessageMapping("math.service.chart")
	public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> requestDtoFlux) {
		return service.chartStream(requestDtoFlux);
	}

}
