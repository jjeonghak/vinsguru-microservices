package project.reactor.sec02;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec08FluxInterval {

	public static void main(String[] args) {

		Flux.interval(Duration.ofMillis(100))
				.subscribe(Util.onNext());

		Util.sleepSeconds(1);

	}

}
