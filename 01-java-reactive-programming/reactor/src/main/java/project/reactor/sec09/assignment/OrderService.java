package project.reactor.sec09.assignment;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class OrderService {

	public static Flux<PurchaseOrder> orderStream() {
		return Flux.interval(Duration.ofMillis(100))
				.map(i -> new PurchaseOrder());
	}

}
