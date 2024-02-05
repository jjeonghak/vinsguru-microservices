package com.vinsguru.userservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.userservice.entity.UserTransaction;

import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Long> {

	Flux<UserTransaction> findByUserId(long userId);

}
