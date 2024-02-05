package com.vinsguru.redisson;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec15SortedSetTest extends BaseTest {

	@Test
	public void sortedSet() {

		RScoredSortedSetReactive<String> sortedSet = this.client.getScoredSortedSet("user:score", StringCodec.INSTANCE);

		//add - just add element
		//addScore - if element already existed, increase its score
		Mono<Void> zadd = sortedSet.addScore("sam", 12.25)
				.then(sortedSet.add(23.25, "mike"))
				.then(sortedSet.addScore("jake", 7))
				.then();

		StepVerifier.create(zadd)
				.verifyComplete();

		sortedSet.entryRange(0, 1)
				.flatMapIterable(Function.identity())
				.map(se -> se.getScore() + " : " + se.getValue())
				.doOnNext(System.out::println)
				.subscribe();

		sleep(1000);

	}

}
