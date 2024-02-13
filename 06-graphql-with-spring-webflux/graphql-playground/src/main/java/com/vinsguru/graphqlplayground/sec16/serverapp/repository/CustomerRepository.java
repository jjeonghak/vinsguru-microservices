package com.vinsguru.graphqlplayground.sec16.serverapp.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.graphqlplayground.sec16.serverapp.entity.Customer;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
}
