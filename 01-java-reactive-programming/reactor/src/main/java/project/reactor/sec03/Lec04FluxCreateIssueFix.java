package project.reactor.sec03;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateIssueFix {

	public static void main(String[] args) {

		//only one instance of fluxsink
		Flux.create(fluxSink -> {
				String country;
				int cnt = 0;
				do {
					country = Util.faker().country().name();
					System.out.println("Emitting : " + country);
					fluxSink.next(country);
					cnt++;
				} while (!country.equalsIgnoreCase("canada") && !fluxSink.isCancelled() && cnt < 10);
				fluxSink.complete();
			})
			.take(3)
			.subscribe(Util.subscriber());

	}

}
