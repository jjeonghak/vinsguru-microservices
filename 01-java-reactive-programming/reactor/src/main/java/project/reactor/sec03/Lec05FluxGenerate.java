package project.reactor.sec03;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxGenerate {

	public static void main(String[] args) {

		//must only one item when use synchronousSink
		Flux.generate(synchronousSink -> {
			System.out.println("Emitting");
			synchronousSink.next(Util.faker().country().name()); //1
			// synchronousSink.next(Util.faker().country().name()); //Error
		})
		.take(2)
		.subscribe(Util.subscriber());

	}

}
