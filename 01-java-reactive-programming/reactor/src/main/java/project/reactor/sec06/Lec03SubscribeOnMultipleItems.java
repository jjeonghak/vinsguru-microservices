package project.reactor.sec06;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

public class Lec03SubscribeOnMultipleItems {

	public static void main(String[] args) {

		Flux<Integer> flux = Flux.create((FluxSink<Integer> fluxSink) -> {
						printMessage("create flux");
						for (int i = 0; i < 20; i++) {
							fluxSink.next(i);
							Util.sleepSeconds(1);
						}
						fluxSink.complete();
				})
				.doOnNext(i -> printMessage("next " + i));

		flux
			.subscribeOn(Schedulers.boundedElastic())
			.subscribe(v -> printMessage("sub " + v));

		flux
			.subscribeOn(Schedulers.parallel())
			.subscribe(v -> printMessage("sub " + v));

		Util.sleepSeconds(5);

	}

	private static void printMessage(String msg) {
		System.out.println(Thread.currentThread().getName() + ": " + msg);
	}

}
