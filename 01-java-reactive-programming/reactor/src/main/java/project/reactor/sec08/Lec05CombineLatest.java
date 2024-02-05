package project.reactor.sec08;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec05CombineLatest {

	public static void main(String[] args) {

		Flux.combineLatest(getString(), getInteger(), (s, i) -> s + i)
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

	private static Flux<String> getString() {
		return Flux.create(stringFluxSink -> {
						char string = 'A';
						for (int i = 0; i < 26; i++) {
							stringFluxSink.next(string++ + "");
						}
						stringFluxSink.complete();
				})
				.cast(String.class)
				.delayElements(Duration.ofSeconds(1));
	}

	private static Flux<Integer> getInteger() {
		return Flux.create(integerFluxSink -> {
						for (int i = 0; i < 10; i++) {
							integerFluxSink.next(i);
						}
						integerFluxSink.complete();
				})
				.cast(Integer.class)
				.delayElements(Duration.ofSeconds(Util.faker().random().nextInt(2, 5)));
	}


}
