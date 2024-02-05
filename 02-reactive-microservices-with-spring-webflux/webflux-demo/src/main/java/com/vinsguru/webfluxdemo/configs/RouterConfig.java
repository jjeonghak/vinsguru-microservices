package com.vinsguru.webfluxdemo.configs;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vinsguru.webfluxdemo.dtos.InputFailedValidationResponse;
import com.vinsguru.webfluxdemo.exceptions.InputValidationException;

import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {

	@Autowired
	private RequestHandler requestHandler;

	//you can delete bean annotation because of highLevelRouter()
	// @Bean
	public RouterFunction<ServerResponse> serverResponseRouterFunction() {
		return RouterFunctions.route()
				.GET("/math/square/{input}", requestHandler::squareHandler)
				.GET("/math/table/{input}", requestHandler::tableHandler)
				.GET("/math/table/stream/{input}", requestHandler::tableStreamHandler)
				.GET("/math/square/validation/{input}", requestHandler::squareHandlerWithValidation)
				// .GET("/math/square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHandler::squareHandler)
				// .GET("/math/square/{input}", req -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
				.POST("/math/multiply/{input}", requestHandler::multiplyHandler)
				.onError(InputValidationException.class, exceptionHandler())
				.build();
	}

	//like @RequestMapping("/router")
	@Bean
	public RouterFunction<ServerResponse> highLevelRouter() {
		return RouterFunctions.route()
				.path("/router", this::serverResponseRouterFunction)
				.build();
	}

	private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
		return (err, req) -> {
				InputValidationException ex = (InputValidationException) err;
				InputFailedValidationResponse response = new InputFailedValidationResponse();
				response.setInput(ex.getInput());
				response.setMessage(ex.getMessage());
				response.setErrorCode(InputValidationException.getErrorCode());
				return ServerResponse.badRequest().bodyValue(response);
		};
	}

}
