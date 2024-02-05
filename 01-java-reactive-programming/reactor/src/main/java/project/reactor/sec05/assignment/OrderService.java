package project.reactor.sec05.assignment;

import java.time.Duration;

import reactor.core.publisher.Flux;

public class OrderService {

	private final static Flux<PurchaseOrder> FLUX = Flux.interval(Duration.ofMillis(100))
			.map(i -> new PurchaseOrder())
			.publish()
			.refCount(2);

	public Flux<PurchaseOrder> orderStream() {
		return FLUX;
	}

	// private Flux<PurchaseOrder> getOrderStream() {
	// 	return Flux.interval(Duration.ofMillis(100))
	// 			.map(i -> new PurchaseOrder())
	// 			.publish()
	// 			.refCount(2);
	// }

}
