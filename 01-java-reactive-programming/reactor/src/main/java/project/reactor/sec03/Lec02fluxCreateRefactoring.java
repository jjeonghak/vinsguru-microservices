package project.reactor.sec03;

import project.reactor.courseutil.Util;
import project.reactor.sec03.helper.NameProducer;
import reactor.core.publisher.Flux;

public class Lec02fluxCreateRefactoring {

	public static void main(String[] args) {

		NameProducer nameProducer = new NameProducer();

		Flux.create(nameProducer)
				.subscribe(Util.subscriber());

		Runnable runnable = nameProducer::produce;

		for (int i = 0; i < 10; i++) {
			new Thread(runnable).start();
		}

		Util.sleepSeconds(2);

	}

}
