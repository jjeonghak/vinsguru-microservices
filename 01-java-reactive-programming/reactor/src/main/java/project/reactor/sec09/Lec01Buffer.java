package project.reactor.sec09;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec01Buffer {

	public static void main(String[] args) {

		eventStream()
				// .buffer(5)
				// .buffer(Duration.ofSeconds(2))
				.bufferTimeout(5, Duration.ofSeconds(2))
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

	private static Flux<String> eventStream() {
		return Flux.interval(Duration.ofMillis(670))
				.map(i -> "event-" + i);
	}

}
