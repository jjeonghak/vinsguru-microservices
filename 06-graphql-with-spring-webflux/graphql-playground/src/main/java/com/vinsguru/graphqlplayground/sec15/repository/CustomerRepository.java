package com.vinsguru.graphqlplayground.sec15.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.vinsguru.graphqlplayground.sec15.entity.Customer;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
}
