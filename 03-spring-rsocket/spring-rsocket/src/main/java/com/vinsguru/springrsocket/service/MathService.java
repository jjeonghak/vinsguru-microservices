package com.vinsguru.springrsocket.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.vinsguru.springrsocket.dto.ChartResponseDto;
import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import com.vinsguru.springrsocket.dto.ComputationResponseDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathService {

	//fire & forget
	public Mono<Void> print(Mono<ComputationRequestDto> requestDtoMono) {
		return requestDtoMono
				.doOnNext(dto -> System.out.println("MathService: received request dto " + dto))
				.then();
	}

	//request & response
	public Mono<ComputationResponseDto> findSquare(Mono<ComputationRequestDto> requestDtoMono) {
		return requestDtoMono
				.map(ComputationRequestDto::getInput)
				.map(i -> new ComputationResponseDto(i, i * i));
	}

	//request stream
	public Flux<ComputationResponseDto> tableStream(ComputationRequestDto dto) {
		return Flux.range(1, 1000)
				.delayElements(Duration.ofSeconds(1))
				.map(i -> new ComputationResponseDto(dto.getInput(), dto.getInput() * i));
	}

	//request channel
	public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> requestDtoFlux) {
		return requestDtoFlux
				.map(ComputationRequestDto::getInput)
				.map(i -> new ChartResponseDto(i, (i * i) + 1))
				.doOnNext(System.out::println);
	}

}
