package com.vinsguru.webfluxdemo.configs;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vinsguru.webfluxdemo.dtos.MultiplyRequestDto;
import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.exceptions.InputValidationException;
import com.vinsguru.webfluxdemo.services.ReactiveMathService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {

	private final ReactiveMathService service;

	public Mono<ServerResponse> squareHandler(ServerRequest request) {
		int input = Integer.parseInt(request.pathVariable("input"));
		Mono<Response> responseMono = service.findSquare(input);
		return ServerResponse
				.ok()
				.body(responseMono, Response.class);
	}

	public Mono<ServerResponse> tableHandler(ServerRequest request) {
		int input = Integer.parseInt(request.pathVariable("input"));
		Flux<Response> responseFlux = service.multiplicationTable(input);
		return ServerResponse
				.ok()
				.body(responseFlux, Response.class);
	}

	public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
		int input = Integer.parseInt(request.pathVariable("input"));
		Flux<Response> responseFlux = service.multiplicationTable(input);
		return ServerResponse
				.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(responseFlux, Response.class);
	}

	public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
		Mono<MultiplyRequestDto> requestMono = request.bodyToMono(MultiplyRequestDto.class);
		Mono<Response> responseFlux = service.multiply(requestMono);
		return ServerResponse
				.ok()
				.body(responseFlux, Response.class);
	}

	public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request) {
		int input = Integer.parseInt(request.pathVariable("input"));
		if (input <= 10 || input > 20) {
			return Mono.error(new InputValidationException(input));
		}
		Mono<Response> responseMono = service.findSquare(input);
		return ServerResponse
				.ok()
				.body(responseMono, Response.class);
	}

}
