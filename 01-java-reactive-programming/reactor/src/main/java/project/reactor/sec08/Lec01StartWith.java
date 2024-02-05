package project.reactor.sec08;

import project.reactor.courseutil.Util;
import project.reactor.sec08.helper.NameGenerator;

public class Lec01StartWith {

	public static void main(String[] args) {

		NameGenerator generator = new NameGenerator();
		generator.generateNames()
				.take(2)
				.subscribe(Util.subscriber("sam"));

		generator.generateNames()
				.take(2)
				.subscribe(Util.subscriber("mike"));

		generator.generateNames()
				.take(3)
				.subscribe(Util.subscriber("jake"));

		generator.generateNames()
				.filter(n -> n.startsWith("A"))
				.take(1)
				.subscribe(Util.subscriber("marshal"));

	}

}
