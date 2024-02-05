package project.reactor.sec03;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerateAssignment {

	public static void main(String[] args) {

		Flux.generate(synchronousSink -> {
				String country = Util.faker().country().name();
				System.out.println("Emitting: " + country);
				synchronousSink.next(country);
				if (country.equalsIgnoreCase("canada")) {
					synchronousSink.complete();
				}
		})
		.subscribe(Util.subscriber());

	}

}
