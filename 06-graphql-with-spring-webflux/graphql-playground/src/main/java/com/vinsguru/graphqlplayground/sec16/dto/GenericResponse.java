package com.vinsguru.graphqlplayground.sec16.dto;

import java.util.List;

import org.springframework.graphql.ResponseError;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GenericResponse<T> {

	private final T data;
	private final List<ResponseError> errors;
	private final boolean dataPresent;

	public GenericResponse(T data, List<ResponseError> errors) {
		this.data = data;
		this.errors = errors;
		this.dataPresent = data != null;
	}

}
