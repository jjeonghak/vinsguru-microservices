package project.reactor.sec08;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec06Assignment {

	public static void main(String[] args) {

		final int carPrice = 10000;
		Flux.combineLatest(
					monthStream(),
					demandStream(),
					(m, d) -> (carPrice - (m * 100)) * d
			)
			.subscribe(Util.subscriber());

		Util.sleepSeconds(20);

	}

	private static Flux<Long> monthStream() {
		return Flux.interval(Duration.ZERO, Duration.ofSeconds(1));
	}

	private static Flux<Double> demandStream() {
		return Flux.interval(Duration.ofSeconds(3))
				.map(i -> Util.faker().random().nextInt(80, 120) / 100d)
				.startWith(1d);
	}

}
