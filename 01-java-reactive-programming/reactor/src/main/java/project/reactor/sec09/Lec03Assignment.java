package project.reactor.sec09;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import project.reactor.courseutil.Util;
import project.reactor.sec09.helper.BookOrder;
import project.reactor.sec09.helper.RevenueReport;
import reactor.core.publisher.Flux;

public class Lec03Assignment {

	public static void main(String[] args) {

		Set<String> allowedCategories = Set.of(
				"Science fiction",
				"Fantasy",
				"Suspense/Thriller"
		);

		bookStream()
				.filter(book -> allowedCategories.contains(book.getCategory()))
				.buffer(Duration.ofSeconds(5))
				.map(Lec03Assignment::revenueCalculator)
				.subscribe(Util.subscriber());

		Util.sleepSeconds(60);

	}

	private static RevenueReport revenueCalculator(List<BookOrder> books) {
		Map<String, Double> revenue = books.stream()
				.collect(Collectors.groupingBy(
						BookOrder::getCategory,
						Collectors.summingDouble(BookOrder::getPrice)
				));

		return new RevenueReport(revenue);
	}

	private static Flux<BookOrder> bookStream() {
		return Flux.interval(Duration.ofMillis(200))
				.map(i -> new BookOrder());
	}

}
