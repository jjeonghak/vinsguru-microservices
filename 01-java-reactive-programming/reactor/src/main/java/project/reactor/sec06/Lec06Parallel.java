package project.reactor.sec06;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec06Parallel {

	public static void main(String[] args) {

		//parallel(1).runOn(Schedulers.parallel()) = subscribeOn(Schedulers.parallel())
		Flux.range(1, 10)
			.parallel()
			.runOn(Schedulers.parallel())
			.doOnNext(i -> printMessage("next " + i))
			.sequential()
			.subscribe(v -> printMessage("sub "+ v));

		Util.sleepSeconds(5);

	}

	private static void printMessage(String msg) {
		System.out.println(Thread.currentThread().getName() + ": " + msg);
	}

}
