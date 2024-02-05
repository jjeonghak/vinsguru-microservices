package project.reactor.sec04;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06OnError {

	public static void main(String[] args) {

		Flux.range(1, 10)
				.log()
				.map(i -> 10 / (3 - i))
				// .onErrorReturn(-1)
				// .onErrorResume(e -> fallback())
				.onErrorContinue((err, obj) -> {
						System.out.println("onErrorContinue: " + obj + " " + err.getMessage());
				})
				.subscribe(Util.subscriber());

	}

	private static Mono<Integer> fallback() {
		return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 200));
	}

}
