package project.reactor.sec02;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxIntro {

	public static void main(String[] args) {

		Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);

		flux.subscribe(
				Util.onNext(),
				Util.onError(),
				Util.onComplete()
		);

	}

}
