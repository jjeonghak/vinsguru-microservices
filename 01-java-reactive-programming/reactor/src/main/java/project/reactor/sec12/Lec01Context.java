package project.reactor.sec12;

import java.util.Map;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class Lec01Context {

	public static void main(String[] args) {

		Map<String, String> map = Map.of(
				"user1", "sam",
				"user2", "jake",
				"user3", "mike"
		);

		getWelcomeMessage()
				.contextWrite(ctx -> ctx.put("user1", ctx.get("user1").toString().toUpperCase()))
				.contextWrite(Context.of(map))
				// .contextWrite(Context.of("user1", "sam"))
				.subscribe(Util.subscriber());

	}

	private static Mono<String> getWelcomeMessage() {
		return Mono.deferContextual(ctx -> {
			if (ctx.hasKey("user1")) {
				return Mono.just("Welcome " + ctx.get("user1"));
			} else {
				return Mono.error(new RuntimeException("unauthenticated"));
			}
		});
	}

}
