package project.reactor.sec09;

import java.time.Duration;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec05Group {

	public static void main(String[] args) {

		Flux.range(1, 30)
				.delayElements(Duration.ofSeconds(1))
				.groupBy(i -> i % 2) //key 0, 1
				.subscribe(gf -> process(gf, gf.key()));

		Util.sleepSeconds(60);

	}

	private static void process(Flux<Integer> flux, int key) {
		System.out.println("Called process: " + key);
		flux.subscribe(i -> System.out.println("Key: " + key + ", Item: " + i));
	}

}
