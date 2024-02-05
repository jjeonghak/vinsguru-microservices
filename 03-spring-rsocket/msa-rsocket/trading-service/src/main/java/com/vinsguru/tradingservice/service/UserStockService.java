package com.vinsguru.tradingservice.service;

import org.springframework.stereotype.Service;

import com.vinsguru.tradingservice.dto.StockTradeRequest;
import com.vinsguru.tradingservice.dto.UserStockDto;
import com.vinsguru.tradingservice.entity.UserStock;
import com.vinsguru.tradingservice.repository.UserStockRepository;
import com.vinsguru.tradingservice.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserStockService {

	private final UserStockRepository stockRepository;

	public Flux<UserStockDto> getUserStocks(String userId) {
		return stockRepository.findByUserId(userId)
				.map(EntityDtoUtil::toUserStockDto);
	}

	public Mono<UserStock> buyStock(StockTradeRequest request) {
		return stockRepository.findByUserIdAndStockSymbol(request.getUserId(), request.getStockSymbol())
				.defaultIfEmpty(EntityDtoUtil.toUserStock(request))
				.doOnNext(us -> us.setQuantity(us.getQuantity() + request.getQuantity()))
				.flatMap(stockRepository::save);
	}

	public Mono<UserStock> sellStock(StockTradeRequest request) {
		return stockRepository.findByUserIdAndStockSymbol(request.getUserId(), request.getStockSymbol())
				.filter(us -> us.getQuantity() >= request.getQuantity())
				.doOnNext(us -> us.setQuantity(us.getQuantity() - request.getQuantity()))
				.flatMap(stockRepository::save);
	}

}
