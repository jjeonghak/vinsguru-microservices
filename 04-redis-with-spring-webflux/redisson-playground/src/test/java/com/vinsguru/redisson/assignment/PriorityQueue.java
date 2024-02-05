package com.vinsguru.redisson.assignment;

import org.redisson.api.RScoredSortedSetReactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PriorityQueue {

	private final RScoredSortedSetReactive<UserOrder> queue;

	public PriorityQueue(RScoredSortedSetReactive<UserOrder> queue) {
		this.queue = queue;
	}

	public Mono<Void> add(UserOrder userOrder) {
		return this.queue.add(getScore(userOrder), userOrder)
				.then();
	}

	public Flux<UserOrder> takeItems() {
		return this.queue.takeFirstElements()
				.limitRate(1);
	}

	private double getScore(UserOrder userOrder) {
		return userOrder.getCategory().getValue() + Double.parseDouble("0." + System.nanoTime());
	}

}
