package com.vinsguru.webfluxdemo.services;

import java.time.Duration;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxdemo.dtos.MultiplyRequestDto;
import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.utils.SleepUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

	public Mono<Response> findSquare(int input) {
		return Mono.fromSupplier(() -> new Response(input * input, LocalDate.now()));
	}

	public Flux<Response> multiplicationTable(int input) {
		return Flux.range(1, 10)
				// .doOnNext(i -> SleepUtil.sleepSeconds(1))
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println("ReactiveMAthService: processing " + i))
				.map(i -> new Response(input * i, LocalDate.now()));
	}

	public Mono<Response> multiply(Mono<MultiplyRequestDto> request) {
		return request.map(dto -> new Response(dto.getFirst() * dto.getSecond(), LocalDate.now()));
	}

}
