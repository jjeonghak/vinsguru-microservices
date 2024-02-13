package com.vinsguru.graphqlplayground.sec01;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class GraphqlController {

	@QueryMapping("basic")
	public Mono<String> basic() {
		return Mono.just("basic")
				.delayElement(Duration.ofMillis(1000));
	}

	@QueryMapping("basicWithArgument")
	public Mono<String> basicWithArgument(@Argument("arg") String value) {
		return Mono.fromSupplier(() -> "basic with " + value)
				.delayElement(Duration.ofMillis(500));
	}

	@QueryMapping("random")
	public Mono<Integer> random() {
		return Mono.just(ThreadLocalRandom.current().nextInt(1, 100))
				.delayElement(Duration.ofMillis(800));
	}

}
