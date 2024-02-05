package com.vinsguru.springrsocket.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class MathClientManager {

	private final Set<RSocketRequester> set = Collections.synchronizedSet(new HashSet<>());

	public void add(RSocketRequester requester) {
		Objects.requireNonNull(requester.rsocket())
				.onClose()
				.doFirst(() -> this.set.add(requester))
				.doFinally(s -> {
						System.out.println("MathClientManager: finally");
						this.set.remove(requester);
				})
				.subscribe();
	}

	public void notify(int i) {
		Flux.fromIterable(set)
				.flatMap(r -> r.route("math.updates").data(i).send())
				.subscribe();
	}

}
