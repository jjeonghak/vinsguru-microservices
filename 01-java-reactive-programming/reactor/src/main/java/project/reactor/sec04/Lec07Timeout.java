package project.reactor.sec04;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec07Timeout {

	public static void main(String[] args) {

		getOrderNumbers()
				.timeout(Duration.ofMillis(1000), fallback())
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

	private static Flux<Integer> getOrderNumbers() {
		return Flux.range(1, 10)
				.concatMap(i -> Flux.just(i).delayElements(Duration.ofMillis(i * 200)));
	}

	private static Flux<Integer> fallback() {
		return Flux.range(100, 10)
				.concatMap(i -> Flux.just(i).delayElements(Duration.ofMillis(i * 20)));
	}

}
