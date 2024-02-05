package project.reactor.sec04;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec03DoCallbacks {

	public static void main(String[] args) {

		Flux.create(fluxSink -> {
					System.out.println("inside create");
					for (int i = 0; i < 5; i++) {
						fluxSink.next(i);
					}
					fluxSink.complete();
					System.out.println("--completed");
			})
			.doOnComplete(() -> System.out.println("doOnComplete"))
			.doFirst(() -> System.out.println("doFirst1"))
			.doOnNext(o -> System.out.println("doOnNext: " + o))
			.doOnSubscribe(s -> System.out.println("doOnSubscribe1: " + s))
			.doOnRequest(l -> System.out.println("doOnRequest: " + l))
			.doOnError(err -> System.out.println("doOnError: " + err.getMessage()))
			.doOnTerminate(() -> System.out.println("doOnTerminate"))
			.doOnCancel(() -> System.out.println("doOnCancel"))
			.doOnSubscribe(s -> System.out.println("doOnSubscribe2: " + s))
			.doFinally(signal -> System.out.println("doFinally1: " + signal))
			.doFirst(() -> System.out.println("doFirst2"))
			.doOnDiscard(Object.class, o -> System.out.println("doOnDiscard: " + 0))
			.take(3)
			.doFinally(signal -> System.out.println("doFinally2: " + signal))
			.subscribe(Util.subscriber());

	}

}
