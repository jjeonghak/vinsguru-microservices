package com.vinsguru.springrsocket.dto;

import java.util.Objects;

import com.vinsguru.springrsocket.dto.error.ErrorEvent;

import lombok.Getter;

@Getter
public class Response<T> {

	ErrorEvent errorResponse;
	T successResponse;

	public Response() {
	}

	public Response(ErrorEvent errorResponse) {
		this.errorResponse = errorResponse;
	}

	public Response(T successResponse) {
		this.successResponse = successResponse;
	}

	public boolean hasError() {
		return Objects.nonNull(this.errorResponse);
	}

	public static <T> Response<T> with(T t) {
		return new Response<T>(t);
	}

	public static <T> Response<T> with(ErrorEvent errorEvent) {
		return new Response<T>(errorEvent);
	}

}
