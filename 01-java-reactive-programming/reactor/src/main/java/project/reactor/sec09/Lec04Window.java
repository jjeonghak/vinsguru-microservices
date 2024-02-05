package project.reactor.sec09;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec04Window {

	private static AtomicInteger atomicInteger = new AtomicInteger(1);

	public static void main(String[] args) {

		eventStream()
				// .window(5)
				.window(Duration.ofSeconds(2))
				.flatMap(Lec04Window::saveEvents)
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

	private static Flux<String> eventStream() {
		return Flux.interval(Duration.ofMillis(500))
				.map(i -> "event-" + i);
	}

	private static Mono<Integer> saveEvents(Flux<String> flux) {
		return flux
				.doOnNext(e -> System.out.println("saving: " + e))
				.doOnComplete(() -> System.out.println("saved this batch"))
				.then(Mono.just(atomicInteger.getAndIncrement()));
	}

}
