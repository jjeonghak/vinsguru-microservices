package project.reactor.sec06;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

public class Lec02SubscribeOnDemo {

	public static void main(String[] args) {

		Flux<Integer> flux = Flux.create((FluxSink<Integer> fluxSink) -> {
						printMessage("create flux");
						fluxSink.next(1);
				})
				.subscribeOn(Schedulers.newParallel("vins"))
				.doOnNext(i -> printMessage("next " + i));

		Runnable runnable = () -> flux
				.doFirst(() -> printMessage("doFirst2"))
				.subscribeOn(Schedulers.boundedElastic())
				.doOnNext(i -> printMessage("next2 " + i))
				.doFirst(() -> printMessage("doFirst1"))
				.subscribe(v -> printMessage("sub " + v));

		for (int i = 0; i < 2; i++) {
			new Thread(runnable).start();
		}

		Util.sleepSeconds(5);

	}

	private static void printMessage(String msg) {
		System.out.println(Thread.currentThread().getName() + ": " + msg);
	}

}
