package project.reactor.sec09.assignment;

import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OrderProcessor {

	public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> automotiveProcessing() {
		return flux -> flux
				.doOnNext(p -> p.setPrice(1.1 * p.getPrice()))
				.doOnNext(p -> p.setItem("[" + p.getItem() + "]"));
	}

	public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> kidsProcessing() {
		return flux -> flux
				.doOnNext(p -> p.setPrice(0.5 * p.getPrice()))
				.flatMap(p -> Flux.concat(Mono.just(p), getFreeKidsOrder()));
	}

	private static Mono<PurchaseOrder> getFreeKidsOrder() {
		return Mono.fromSupplier(() -> {
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				purchaseOrder.setItem("FREE-" + purchaseOrder.getItem());
				purchaseOrder.setPrice(0d);
				purchaseOrder.setCategory("Kids");
				return purchaseOrder;
		});
	}

}
