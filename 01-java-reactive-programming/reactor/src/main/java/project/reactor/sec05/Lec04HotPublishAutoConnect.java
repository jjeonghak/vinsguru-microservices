package project.reactor.sec05;

import java.time.Duration;
import java.util.stream.Stream;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec04HotPublishAutoConnect {

	public static void main(String[] args) {

		Flux<String> movieStream = Flux.fromStream(Lec04HotPublishAutoConnect::getMovie)
				.delayElements(Duration.ofSeconds(1))
				.publish()
				.autoConnect(0);

		Util.sleepSeconds(3);

		System.out.println("Sam is about to join");

		movieStream.subscribe(Util.subscriber("sam"));

		Util.sleepSeconds(10);

		System.out.println("Mike is about to join");

		movieStream.subscribe(Util.subscriber("mike"));

		Util.sleepSeconds(60);

	}

	//movie theater
	private static Stream<String> getMovie() {
		System.out.println("Got the movie streaming req");
		return Stream.of(
				"Scene1",
				"Scene2",
				"Scene3",
				"Scene4",
				"Scene5",
				"Scene6"
		);
	}

}
