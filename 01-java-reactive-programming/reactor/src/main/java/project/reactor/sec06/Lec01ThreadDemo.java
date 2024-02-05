package project.reactor.sec06;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class Lec01ThreadDemo {

	public static void main(String[] args) {

		Flux<Integer> flux = Flux.create((FluxSink<Integer> fluxSink) -> {
						printMessage("create flux");
						fluxSink.next(1);
				})
				.doOnNext(i -> printMessage("next " + i));

		Runnable runnable = () -> flux.subscribe(v -> printMessage("sub " + v));
		for (int i = 0; i < 2; i++) {
			new Thread(runnable).start();
		}

		Util.sleepSeconds(60);

	}

	private static void printMessage(String msg) {
		System.out.println(Thread.currentThread().getName() + ": " + msg);
	}

}
