package project.reactor.sec01;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec06SupplierRefactoring {

	public static void main(String[] args) {

		Mono<String> mono = getName();
		mono.subscribe(Util.onNext());

		Mono<String> schedulerMono = getName();
		schedulerMono
				.subscribeOn(Schedulers.boundedElastic())
				.subscribe(Util.onNext());

		System.out.println("Main thread done");
		Util.sleepSeconds(4);

	}

	private static Mono<String> getName() {
		System.out.println("entered getName method");
		return Mono.fromSupplier(() -> {
			System.out.println("Generating name..");
			Util.sleepSeconds(3);
			return Util.faker().name().fullName();
		}).map(String::toUpperCase);
	}

}
