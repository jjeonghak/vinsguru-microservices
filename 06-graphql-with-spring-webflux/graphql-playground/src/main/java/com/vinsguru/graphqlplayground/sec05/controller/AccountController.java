package com.vinsguru.graphqlplayground.sec05.controller;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.vinsguru.graphqlplayground.sec05.dto.Customer;
import com.vinsguru.graphqlplayground.sec05.dto.Account;

import graphql.schema.DataFetchingFieldSelectionSet;
import reactor.core.publisher.Mono;

@Controller
public class AccountController {

	@SchemaMapping(typeName = "Customer")
	public Mono<Account> account(Customer customer, DataFetchingFieldSelectionSet selectionSet) {
		System.out.println("AccountController: account " + selectionSet.getFields());
		return Mono.just(
				Account.create(
						UUID.randomUUID().toString(),
						ThreadLocalRandom.current().nextInt(1, 1000),
						ThreadLocalRandom.current().nextBoolean() ? "CHECKING" : "SAVING"
				)
		);
	}

}
