package com.vinsguru.springrsocket.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

	EC001("Given number is not within range"),
	EC002("Your usage limit exceeded");

	private final String description;

}
