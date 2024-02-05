package com.vinsguru.tradingservice.client;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import com.vinsguru.tradingservice.dto.StockTickDto;

import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

@Service
public class StockClient {

	private final RSocketRequester requester;
	private final Flux<StockTickDto> stockStream;
	private final Map<String, Integer> map;

	public StockClient(
			RSocketRequester.Builder builder,
			RSocketConnectorConfigurer connectorConfigurer,
			@Value("${stock.service.host}") String host,
			@Value("${stock.service.port}") int port) {
		//connection retry
		this.requester = builder.rsocketConnector(connectorConfigurer)
				.transport(TcpClientTransport.create(host, port));

		this.map = new HashMap<>();

		//stream retry
		this.stockStream = requester.route("stock.ticks")
				.retrieveFlux(StockTickDto.class)
				.doOnNext(s -> this.map.put(s.getCode(), s.getPrice()))
				.retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(2)))
				.publish()
				.autoConnect(0);
	}

	public Flux<StockTickDto> getStockStream() {
		return stockStream;
	}

	public int getCurrentStockPrice(String stockSymbol) {
		return map.getOrDefault(stockSymbol, 0);
	}

}
