package project.reactor.sec05;

import java.time.Duration;
import java.util.stream.Stream;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec03HotPublish {

	public static void main(String[] args) {

		//share = publish().refCount(1)
		Flux<String> movieStream = Flux.fromStream(Lec03HotPublish::getMovie)
				.delayElements(Duration.ofSeconds(2))
				.publish()
				.refCount(2);

		movieStream.subscribe(Util.subscriber("sam"));

		Util.sleepSeconds(5);

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
