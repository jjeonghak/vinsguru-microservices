package project.reactor.sec01;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Mono;

public class Lec05MonoFromSupplier {

	public static void main(String[] args) {

		//use just only when you have data already
		Mono<String> just = Mono.just(getName());

		Supplier<String> supplier = Lec05MonoFromSupplier::getName;
		Mono<String> supplierMono = Mono.fromSupplier(supplier);
		supplierMono.subscribe(Util.onNext());

		Callable<String> callable = Lec05MonoFromSupplier::getName;
		Mono<String> callableMono = Mono.fromCallable(callable);
		callableMono.subscribe(Util.onNext());

	}

	private static String getName() {
		System.out.println("Generating name..");
		return Util.faker().name().fullName();
	}

}
