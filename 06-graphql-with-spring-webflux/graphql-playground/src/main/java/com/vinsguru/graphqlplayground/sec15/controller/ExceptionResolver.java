package com.vinsguru.graphqlplayground.sec15.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec15.exception.ApplicationException;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

@Service
public class ExceptionResolver implements DataFetcherExceptionResolver {

	@Override
	public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {
		var ex = toApplicationException(exception);
		return Mono.just(
				List.of(
						GraphqlErrorBuilder.newError(environment)
								.message(ex.getMessage())
								.errorType(ex.getErrorType())
								.extensions(ex.getExtensions())
								.build()
				)
		);
	}

	private ApplicationException toApplicationException(Throwable throwable) {
		return ApplicationException.class.equals(throwable.getClass()) ?
				(ApplicationException) throwable :
				new ApplicationException(
						ErrorType.INTERNAL_ERROR,
						throwable.getMessage(),
						Collections.emptyMap()
				);
	}

}
