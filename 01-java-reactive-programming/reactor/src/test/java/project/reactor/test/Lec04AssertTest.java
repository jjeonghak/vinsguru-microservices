package project.reactor.test;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import project.reactor.sec09.helper.BookOrder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04AssertTest {

	@Test
	public void test1() {
		Mono<BookOrder> mono = Mono.fromSupplier(BookOrder::new);

		StepVerifier.create(mono)
				.assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
				.verifyComplete();

	}

	@Test
	public void test2() {
		Mono<BookOrder> mono = Mono.fromSupplier(BookOrder::new)
					.delayElement(Duration.ofSeconds(3));

		StepVerifier.create(mono)
				.assertNext(b -> Assertions.assertNotNull(b.getAuthor()))
				.expectComplete()
				.verify(Duration.ofSeconds(4));

	}

}
