package project.reactor.sec04;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec05Delay {

	public static void main(String[] args) {

		//set queue buffer size, default 32
		System.setProperty("reactor.bufferSize.x", "9");

		Flux.range(1, 100)
				.log()
				.delayElements(Duration.ofMillis(100))
				.subscribe(Util.subscriber());

		Util.sleepSeconds(15);
	}

}
