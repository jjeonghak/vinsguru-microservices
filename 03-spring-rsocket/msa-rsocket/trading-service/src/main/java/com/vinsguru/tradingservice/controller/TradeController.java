package com.vinsguru.tradingservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.tradingservice.client.StockClient;
import com.vinsguru.tradingservice.dto.StockTickDto;
import com.vinsguru.tradingservice.dto.StockTradeRequest;
import com.vinsguru.tradingservice.dto.StockTradeResponse;
import com.vinsguru.tradingservice.service.TradeService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class TradeController {

	private final TradeService tradeService;
	private final StockClient stockClient;
	
	@GetMapping(value = "/tick/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<StockTickDto> stockTicks() {
		return stockClient.getStockStream();
	}

	@PostMapping("/trade")
	public Mono<ResponseEntity<StockTradeResponse>> trade(@RequestBody Mono<StockTradeRequest> tradeRequestMono) {
		return tradeRequestMono
				.filter(tr -> tr.getQuantity() > 0)
				.flatMap(tradeService::trade)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

}
