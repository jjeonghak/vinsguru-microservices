package com.vinsguru.graphqlplayground.sec15.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.graphql.execution.ErrorType;

import com.vinsguru.graphqlplayground.sec15.dto.CustomerDto;

import reactor.core.publisher.Mono;

public class ApplicationErrors {

	public static <T> Mono<T> noSuchUser(Long id) {
		return Mono.error(new ApplicationException(
				ErrorType.BAD_REQUEST,
				"ApplicationErrors: no such user",
				Map.of(
						"customerId", id,
						"timestamp", LocalDateTime.now()
				)
		));
	}

	public static <T> Mono<T> mustBe18(CustomerDto dto) {
		return Mono.error(new ApplicationException(
				ErrorType.BAD_REQUEST,
				"ApplicationErrors: must be 18 or above",
				Map.of(
						"input", dto,
						"timestamp", LocalDateTime.now()
				)
		));
	}

}
