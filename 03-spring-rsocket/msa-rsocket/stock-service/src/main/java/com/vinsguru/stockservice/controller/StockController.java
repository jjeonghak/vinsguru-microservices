package com.vinsguru.stockservice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.stockservice.dto.StockTickDto;
import com.vinsguru.stockservice.service.StockService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class StockController {

	private final StockService service;

	@MessageMapping("stock.ticks")
	public Flux<StockTickDto> stockPrice() {
		return service.getStockPrice();
	}

}
