package project.reactor.sec03;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateCounter {

	public static void main(String[] args) {

		Flux.generate(
				() -> 1,
				(cnt, sink) -> {
						String country = Util.faker().country().name();
						sink.next(country);
						if (cnt >= 10 || country.equalsIgnoreCase("canada")) {
							sink.complete();
						}
						return cnt + 1;
				}
		)
		.subscribe(Util.subscriber());

	}

}
