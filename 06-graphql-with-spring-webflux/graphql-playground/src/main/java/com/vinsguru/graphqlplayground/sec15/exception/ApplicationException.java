package com.vinsguru.graphqlplayground.sec15.exception;

import java.util.Map;

import org.springframework.graphql.execution.ErrorType;

public class ApplicationException extends RuntimeException {

	private final ErrorType errorType;
	private final Map<String, Object> extensions;

	public ApplicationException(ErrorType errorType, String message, Map<String, Object> extensions) {
		super(message);
		this.errorType = errorType;
		this.extensions = extensions;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public Map<String, Object> getExtensions() {
		return extensions;
	}

}
