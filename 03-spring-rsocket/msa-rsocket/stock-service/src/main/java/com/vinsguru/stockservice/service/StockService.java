package com.vinsguru.stockservice.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.vinsguru.stockservice.dto.StockTickDto;

import reactor.core.publisher.Flux;

@Service
public class StockService {

	private static final Stock AMZN = new Stock(1000, "AMZN", 20);
	private static final Stock AAPL = new Stock(100, "AAPL", 3);
	private static final Stock MSFT = new Stock(200, "MSFT", 5);

	public Flux<StockTickDto> getStockPrice() {
		return Flux.interval(Duration.ofSeconds(2))
				.concatMap(i -> Flux.just(AMZN, AAPL, MSFT))
				.map(s -> new StockTickDto(s.getCode(), s.getPrice()));
	}

}
