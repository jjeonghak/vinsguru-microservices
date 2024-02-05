package com.vinsguru.tradingservice.util;

import org.springframework.beans.BeanUtils;

import com.vinsguru.tradingservice.dto.StockTradeRequest;
import com.vinsguru.tradingservice.dto.StockTradeResponse;
import com.vinsguru.tradingservice.dto.TradeStatus;
import com.vinsguru.tradingservice.dto.TradeType;
import com.vinsguru.tradingservice.dto.TransactionRequest;
import com.vinsguru.tradingservice.dto.TransactionType;
import com.vinsguru.tradingservice.dto.UserStockDto;
import com.vinsguru.tradingservice.entity.UserStock;

public class EntityDtoUtil {

	public static TransactionRequest toTransactionRequest(StockTradeRequest stockTradeRequest, int amount) {
		TransactionRequest transactionRequest = new TransactionRequest();
		TransactionType type = stockTradeRequest.getTradeType().equals(TradeType.BUY) ? TransactionType.DEBIT : TransactionType.CREDIT;

		transactionRequest.setType(type);
		transactionRequest.setUserId(stockTradeRequest.getUserId());
		transactionRequest.setAmount(amount);
		return transactionRequest;
	}

	public static StockTradeResponse toTradeResponse(
			StockTradeRequest stockTradeRequest,
			TradeStatus status,
			int price) {
		StockTradeResponse stockTradeResponse = new StockTradeResponse();
		BeanUtils.copyProperties(stockTradeRequest, stockTradeResponse);
		stockTradeResponse.setTradeStatus(status);
		stockTradeResponse.setPrice(price);
		return stockTradeResponse;
	}

	public static UserStock toUserStock(StockTradeRequest request) {
		UserStock stock = new UserStock();
		stock.setUserId(request.getUserId());
		stock.setStockSymbol(request.getStockSymbol());
		stock.setQuantity(0);
		return stock;
	}

	public static UserStockDto toUserStockDto(UserStock userStock) {
		UserStockDto dto = new UserStockDto();
		BeanUtils.copyProperties(userStock, dto);
		return dto;
	}

}
