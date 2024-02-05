package project.reactor.sec07;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec04Error {

	public static void main(String[] args) {

		//75% -> 12
		System.setProperty("reactor.bufferSize.small", "16");

		Flux.create(fluxSink -> {
						for (int i = 1; i < 501 && !fluxSink.isCancelled(); i++) {
							System.out.println("Pushed: " + i);
							fluxSink.next(i);
							Util.sleepMillis(1);
						}
						fluxSink.complete();
				})
				.onBackpressureError()
				.publishOn(Schedulers.boundedElastic())
				.doOnNext(i -> Util.sleepMillis(10))
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

}
