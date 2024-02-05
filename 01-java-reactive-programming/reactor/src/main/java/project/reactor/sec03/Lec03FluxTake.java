package project.reactor.sec03;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec03FluxTake {

	public static void main(String[] args) {

		Flux.range(1, 10)
				.log()
				.filter(i -> i % 2 == 0)
				.log()
				.take(3)
				.log()
				.subscribe(Util.subscriber());

	}

}
