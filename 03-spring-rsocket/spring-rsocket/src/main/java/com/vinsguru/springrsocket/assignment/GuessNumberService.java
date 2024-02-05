package com.vinsguru.springrsocket.assignment;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class GuessNumberService {

	public Flux<GuessNumberResponse> play(Flux<Integer> flux) {
		int serverNumber = ThreadLocalRandom.current().nextInt(1, 100);
		System.out.println("GuessNumberService: server number is " + serverNumber);
		return flux
				.map(i -> this.compare(serverNumber, i));
	}

	private GuessNumberResponse compare(int serverNumber, int clientNumber) {
		if (serverNumber > clientNumber) {
			return GuessNumberResponse.GREATER;
		} else if (serverNumber < clientNumber) {
			return GuessNumberResponse.LESSER;
		} else {
			return GuessNumberResponse.EQUAL;
		}
	}

}
