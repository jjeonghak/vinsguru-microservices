package com.vinsguru.tradingservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.tradingservice.entity.UserStock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserStockRepository extends ReactiveMongoRepository<UserStock, String> {

	Mono<UserStock> findByUserIdAndStockSymbol(String userId, String stockSymbol);

	Flux<UserStock> findByUserId(String userId);

}
