package project.reactor.sec08.helper;

import java.util.ArrayList;
import java.util.List;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Flux;

public class NameGenerator {

	private final List<String> CACHE = new ArrayList<>();

	public Flux<String> generateNames() {
		return Flux.generate(stringSynchronousSink -> {
				System.out.println("generate fresh");
				Util.sleepSeconds(1);
				String name = Util.faker().name().fullName();
				CACHE.add(name);
				stringSynchronousSink.next(name);
		})
		.cast(String.class)
		.startWith(getFromCache());
	}

	private Flux<String> getFromCache() {
		return Flux.fromIterable(CACHE);
	}

}
