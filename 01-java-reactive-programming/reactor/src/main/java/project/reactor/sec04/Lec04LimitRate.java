package project.reactor.sec04;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec04LimitRate {

	public static void main(String[] args) {


		Flux.range(1, 1000)
				.log()
				// .limitRate(100) //75%
				// .limitRate(100, 100) //75%
				.limitRate(100, 99) //99%
				.subscribe(Util.subscriber());

	}

}
