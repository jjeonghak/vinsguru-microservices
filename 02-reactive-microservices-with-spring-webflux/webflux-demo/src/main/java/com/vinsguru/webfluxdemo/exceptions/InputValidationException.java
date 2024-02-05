package com.vinsguru.webfluxdemo.exceptions;

public class InputValidationException extends RuntimeException {

	private static final String MSG = "Allowed range is 10 - 20";
	private static final int ERROR_CODE = 100;
	private final int input;

	public InputValidationException(int input) {
		super(MSG);
		this.input = input;
	}

	public static int getErrorCode() {
		return ERROR_CODE;
	}

	public int getInput() {
		return input;
	}
}
