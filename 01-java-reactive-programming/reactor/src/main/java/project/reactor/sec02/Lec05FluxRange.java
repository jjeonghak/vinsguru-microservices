package project.reactor.sec02;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {

	public static void main(String[] args) {

		Flux.range(0, 10)
				.log()
				.map(i -> Util.faker().name().fullName())
				.log()
				.subscribe(Util.onNext());

	}

}
