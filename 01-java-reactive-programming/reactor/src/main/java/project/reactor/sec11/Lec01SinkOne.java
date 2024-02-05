package project.reactor.sec11;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {

	public static void main(String[] args) {

		//mono 1 value / empty / error
		Sinks.One<Object> sink = Sinks.one();

		Mono<Object> mono = sink.asMono();

		mono.subscribe(Util.subscriber("sam"));
		mono.subscribe(Util.subscriber("mike"));

		// sink.tryEmitValue("value1");
		// sink.tryEmitEmpty();
		// sink.tryEmitError(new RuntimeException("oops"));

		sink.emitValue("value", ((signalType, emitResult) -> {
				System.out.println(signalType.name());
				System.out.println(emitResult.name());
				return false;
		}));

		sink.emitValue("done", ((signalType, emitResult) -> {
				System.out.println(signalType.name());
				System.out.println(emitResult.name());
				return false;
		}));

	}

}
