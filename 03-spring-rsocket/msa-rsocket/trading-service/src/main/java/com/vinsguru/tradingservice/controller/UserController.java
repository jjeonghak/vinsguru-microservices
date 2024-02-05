package com.vinsguru.tradingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.tradingservice.client.UserClient;
import com.vinsguru.tradingservice.dto.UserDto;
import com.vinsguru.tradingservice.dto.UserStockDto;
import com.vinsguru.tradingservice.service.UserStockService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserStockService stockService;
	private final UserClient userClient;

	@GetMapping("/all")
	public Flux<UserDto> allUsers() {
		return userClient.allUsers();
	}

	@GetMapping("/stocks/{userId}")
	public Flux<UserStockDto> getUserStocks(@PathVariable("userId") String id) {
		return stockService.getUserStocks(id);
	}

}
