package com.vinsguru.springrsocket.assignment;

import java.util.function.Consumer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class Player {

	private final Sinks.Many<Integer> sink = Sinks.many().unicast().onBackpressureBuffer();
	private int lower = 0;
	private int upper = 100;
	private int mid = 0;
	private int attempts = 0;

	public void play() {
		this.emit();
	}

	public Flux<Integer> guesses() {
		return this.sink.asFlux();
	}

	public Consumer<GuessNumberResponse> receives() {
		return this::processResponse;
	}

	private void processResponse(GuessNumberResponse numberResponse) {

		attempts++;
		System.out.println("Player: attempts " + attempts + ", " + mid + ", " + numberResponse);

		if (numberResponse.equals(GuessNumberResponse.EQUAL)) {
			this.sink.tryEmitComplete();
			return;
		} else if (numberResponse.equals(GuessNumberResponse.GREATER)) {
			lower = mid;
		} else if (numberResponse.equals(GuessNumberResponse.LESSER)) {
			upper = mid;
		}

		emit();
	}

	private void emit() {
		mid = lower + (upper - lower) / 2;
		this.sink.tryEmitNext(mid);
	}

}
