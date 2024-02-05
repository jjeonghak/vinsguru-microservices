package project.reactor.sec01;

import java.util.concurrent.CompletableFuture;

import project.reactor.courseutil.Util;
import reactor.core.publisher.Mono;

public class Lec07MonoFromFuture {

	public static void main(String[] args) {

		Mono.fromFuture(Lec07MonoFromFuture::getName)
				.subscribe(Util.onNext());

		Util.sleepSeconds(1);

	}

	private static CompletableFuture<String> getName() {
		return CompletableFuture.supplyAsync(() -> Util.faker().name().fullName());
	}

}
