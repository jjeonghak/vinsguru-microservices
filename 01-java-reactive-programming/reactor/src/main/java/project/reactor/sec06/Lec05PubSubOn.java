package project.reactor.sec06;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

public class Lec05PubSubOn {

	public static void main(String[] args) {

		Flux<Integer> flux = Flux.create((FluxSink<Integer> fluxSink) -> {
						printMessage("create flux");
						for (int i = 0; i < 2; i++) {
							fluxSink.next(i);
						}
						fluxSink.complete();
				})
				.doOnNext(i -> printMessage("next1 " + i));

		flux
			.publishOn(Schedulers.boundedElastic())
			.doOnNext(i -> printMessage("next2 " + i))
			.subscribeOn(Schedulers.parallel())
			.subscribe(v -> printMessage("sub " + v));

		Util.sleepSeconds(5);

	}

	private static void printMessage(String msg) {
		System.out.println(Thread.currentThread().getName() + ": " + msg);
	}

}
