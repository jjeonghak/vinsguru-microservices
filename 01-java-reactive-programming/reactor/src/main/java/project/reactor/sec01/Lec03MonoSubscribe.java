package project.reactor.sec01;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {

	public static void main(String[] args) {

		//publisher
		Mono<Integer> mono = Mono.just("ball")
					.map(String::length)
					.map(l -> l / 0);

		// mono.subscribe();

		mono.subscribe(
				Util.onNext(),
				Util.onError(),
				Util.onComplete()
		);

	}

}
