package project.reactor.sec08;

import project.reactor.courseutil.Util;
import project.reactor.sec08.helper.AmericanAirlines;
import project.reactor.sec08.helper.Emirates;
import project.reactor.sec08.helper.Qatar;
import reactor.core.publisher.Flux;

public class Lec03Merge {

	public static void main(String[] args) {

		Flux<String> merge = Flux.merge(
				Qatar.getFlights(),
				Emirates.getFlights(),
				AmericanAirlines.getFlights()
		);

		merge.subscribe(Util.subscriber());

		Util.sleepSeconds(10);

	}

}
