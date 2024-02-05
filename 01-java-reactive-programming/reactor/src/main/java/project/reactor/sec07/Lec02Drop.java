package project.reactor.sec07;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

public class Lec02Drop {

	public static void main(String[] args) {

		//75% -> 12
		System.setProperty("reactor.bufferSize.small", "16");

		Flux.create(fluxSink -> {
						for (int i = 1; i < 501; i++) {
							System.out.println("Pushed: " + i);
							fluxSink.next(i);
							Util.sleepMillis(1);
						}
						fluxSink.complete();
				})
				.onBackpressureDrop(d -> System.out.println("Dropped: " + d))
				.publishOn(Schedulers.boundedElastic())
				.doOnNext(i -> Util.sleepMillis(10))
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

}
