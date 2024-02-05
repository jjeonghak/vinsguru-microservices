package project.reactor.sec08.helper;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class AmericanAirlines {

	public static Flux<String> getFlights() {
		return Flux.range(1, Util.faker().random().nextInt(1, 10))
				.delayElements(Duration.ofSeconds(1))
				.map(i -> "AmericanAirlines " + Util.faker().random().nextInt(100, 999))
				.filter(s -> Util.faker().random().nextBoolean())
				.doOnNext(s -> System.out.println(Thread.currentThread().getName() + ": do on next " + s));
	}

}
