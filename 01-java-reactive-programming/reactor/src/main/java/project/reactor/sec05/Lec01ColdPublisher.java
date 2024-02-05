package project.reactor.sec05;

import java.time.Duration;
import java.util.stream.Stream;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec01ColdPublisher {

	public static void main(String[] args) {

		Flux<String> movieStream = Flux.fromStream(Lec01ColdPublisher::getMovie)
				.delayElements(Duration.ofSeconds(2));

		movieStream.subscribe(Util.subscriber("sam"));

		Util.sleepSeconds(5);

		movieStream.subscribe(Util.subscriber("mike"));

		Util.sleepSeconds(60);

	}

	//netflix
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
