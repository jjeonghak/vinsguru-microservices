package com.vinsguru.springrsocket.assignment;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class GuessNumberController {

	private final GuessNumberService service;

	@MessageMapping("guess.a.number")
	public Flux<GuessNumberResponse> play(Flux<Integer> flux) {
		return service.play(flux);
	}

}
