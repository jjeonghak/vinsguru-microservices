package com.vinsguru.productservice.service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.vinsguru.productservice.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

	private final ProductService service;

	@Override
	public void run(String... args) throws Exception {
		ProductDto p1 = ProductDto.of("LG-TV", 1500);
		ProductDto p2 = ProductDto.of("SAMSUNG-TV", 1500);
		ProductDto p3 = ProductDto.of("SAMSUNG-phone", 120);
		ProductDto p4 = ProductDto.of("headphone", 40);

		// Flux.just(p1, p2, p3, p4)
		// 		.concatWith(newProducts())
		// 		.flatMap(p -> this.service.insertProduct(Mono.just(p)))
		// 		.subscribe(System.out::println);

	}

	private Flux<ProductDto> newProducts() {
		return Flux.range(1, 1000)
				.delayElements(Duration.ofSeconds(2))
				.map(i -> ProductDto.of("product-" + i, ThreadLocalRandom.current().nextInt(10, 100)));
	}

}
