package com.vinsguru.webfluxdemo.advices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vinsguru.webfluxdemo.dtos.InputFailedValidationResponse;
import com.vinsguru.webfluxdemo.exceptions.InputValidationException;

@ControllerAdvice
public class InputValidationHandler {

	@ExceptionHandler(InputValidationException.class)
	public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException exception) {
		InputFailedValidationResponse response = new InputFailedValidationResponse();
		response.setErrorCode(InputValidationException.getErrorCode());
		response.setInput(exception.getInput());
		response.setMessage(exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(response);
	}

}
