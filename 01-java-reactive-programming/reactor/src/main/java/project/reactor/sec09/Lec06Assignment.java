package project.reactor.sec09;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import project.reactor.courseutil.Util;
import project.reactor.sec09.assignment.OrderProcessor;
import project.reactor.sec09.assignment.OrderService;
import project.reactor.sec09.assignment.PurchaseOrder;
import reactor.core.publisher.Flux;

public class Lec06Assignment {

	public static void main(String[] args) {

		Map<String, Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>>> map = Map.of(
				"Kids", OrderProcessor.kidsProcessing(),
				"Automotive", OrderProcessor.automotiveProcessing()
		);

		Set<String> keys = map.keySet();

		OrderService.orderStream()
				.filter(p -> keys.contains(p.getCategory()))
				.groupBy(PurchaseOrder::getCategory)
				.flatMap(gf -> map.get(gf.key()).apply(gf))
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

}
