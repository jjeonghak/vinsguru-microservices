package project.reactor.sec08;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec02Concat {

	public static void main(String[] args) {

		Flux<String> flux1 = Flux.just("a", "b");
		Flux<String> flux2 = Flux.just("c", "d", "e");
		Flux<String> flux3 = Flux.error(new RuntimeException("oops"));

		Flux<String> concatWith = flux1.concatWith(flux2);
		Flux<String> concat = Flux.concat(flux1, flux3, flux2);
		Flux<String> concatDelayError = Flux.concatDelayError(flux1, flux3, flux2);

		concatWith.subscribe(Util.subscriber("concatWith"));
		concat.subscribe(Util.subscriber("concat"));
		concatDelayError.subscribe(Util.subscriber("concatDelayError"));

	}

}
