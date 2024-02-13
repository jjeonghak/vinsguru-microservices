package com.vinsguru.graphqlplayground.sec12.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.ExecutionInput;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;

@Configuration
public class OperationCachingConfig {

	/**
	 * Graphql works flow
	 *
	 * 1. request body
	 * 2. exe doc
	 *
	 * 3. parse
	 * 4. validation
	 * (we can cache this two steps -> 3, 4)
	 *
	 * 5. exe doc
	 *
	 * suggestion: use variables along with operation name
	 * prefer: https://github.com/ben-manes/caffeine
	 *
	 */

	@Bean
	public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer(PreparsedDocumentProvider provider) {
		return c -> c.configureGraphQl(b -> b.preparsedDocumentProvider(provider));
	}

	@Bean
	public PreparsedDocumentProvider provider() {
		return new PreparsedDocumentProvider() {

			private final Map<String, PreparsedDocumentEntry> map = new ConcurrentHashMap<>();

			@Override
			public PreparsedDocumentEntry getDocument(
					ExecutionInput executionInput,
					Function<ExecutionInput, PreparsedDocumentEntry> parseAndValidateFunction) {
				return map.computeIfAbsent(executionInput.getQuery(), key -> {
						System.out.println("PreparsedDocumentProvider: not found " + key);
						var r = parseAndValidateFunction.apply(executionInput);
						System.out.println("PreparsedDocumentProvider: caching " + r);
						return r;
				});
			}

		};
	}

}
