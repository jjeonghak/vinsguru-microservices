package com.vinsguru.tradingservice.service;

import org.springframework.stereotype.Service;

import com.vinsguru.tradingservice.client.StockClient;
import com.vinsguru.tradingservice.client.UserClient;
import com.vinsguru.tradingservice.dto.StockTradeRequest;
import com.vinsguru.tradingservice.dto.StockTradeResponse;
import com.vinsguru.tradingservice.dto.TradeStatus;
import com.vinsguru.tradingservice.dto.TradeType;
import com.vinsguru.tradingservice.dto.TransactionRequest;
import com.vinsguru.tradingservice.dto.TransactionStatus;
import com.vinsguru.tradingservice.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TradeService {

	private final UserStockService stockService;
	private final UserClient userClient;
	private final StockClient stockClient;

	public Mono<StockTradeResponse> trade(StockTradeRequest tradeRequest) {
		TransactionRequest transactionRequest = EntityDtoUtil.toTransactionRequest(tradeRequest, estimatePrice(tradeRequest));
		Mono<StockTradeResponse> responseMono = tradeRequest.getTradeType().equals(TradeType.BUY) ?
				buyStock(tradeRequest, transactionRequest) :
				sellStock(tradeRequest, transactionRequest);

		return responseMono
				.defaultIfEmpty(EntityDtoUtil.toTradeResponse(tradeRequest, TradeStatus.FAILED, 0));
	}

	private Mono<StockTradeResponse> buyStock(StockTradeRequest tradeRequest, TransactionRequest transactionRequest) {
		return userClient.doTransaction(transactionRequest)
				.filter(tr -> tr.getStatus().equals(TransactionStatus.COMPLETED))
				.flatMap(tr -> stockService.buyStock(tradeRequest))
				.map(us -> EntityDtoUtil.toTradeResponse(tradeRequest, TradeStatus.COMPLETED, transactionRequest.getAmount()));
	}

	private Mono<StockTradeResponse> sellStock(StockTradeRequest tradeRequest, TransactionRequest transactionRequest) {
		return stockService.sellStock(tradeRequest)
				.flatMap(us -> userClient.doTransaction(transactionRequest))
				.map(tr -> EntityDtoUtil.toTradeResponse(tradeRequest, TradeStatus.COMPLETED, transactionRequest.getAmount()));
	}

	private int estimatePrice(StockTradeRequest request) {
		return request.getQuantity() * stockClient.getCurrentStockPrice(request.getStockSymbol());
	}



}
