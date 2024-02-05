package project.reactor.sec08;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec04Zip {

	public static void main(String[] args) {

		Flux.zip(getBody(), getEngine(), getTires())
			.subscribe(Util.subscriber());

		Util.sleepSeconds(20);

	}

	private static Flux<String> getBody() {
		return Flux.range(1, 5)
				.map(i -> "body-" + i)
				.delayElements(Duration.ofSeconds(1));
	}

	private static Flux<String> getEngine() {
		return Flux.range(1, 3)
				.map(i -> "engine-" + i)
			.delayElements(Duration.ofSeconds(3));
	}

	private static Flux<String> getTires() {
		return Flux.range(1, 5)
				.map(i -> "tires-" + i)
				.delayElements(Duration.ofSeconds(2));
	}

}
