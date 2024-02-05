package project.reactor.sec02;

import java.util.List;
import java.util.stream.Stream;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec04FluxFromStream {

	public static void main(String[] args) {

		List<Integer> list = List.of(1, 2, 3, 4, 5);
		Stream<Integer> stream = list.stream();

		// Flux<Integer> integerFlux = Flux.fromStream(stream);
		Flux<Integer> integerFlux = Flux.fromStream(list::stream);

		integerFlux.subscribe(
				Util.onNext(),
				Util.onError(),
				Util.onComplete()
		);

		integerFlux.subscribe(
				Util.onNext(),
				Util.onError(),
				Util.onComplete()
		);

	}

}
