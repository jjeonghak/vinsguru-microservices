package com.vinsguru.webfluxdemo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculatorRouterConfig {

	@Autowired
	private CalculatorHandler requestHandler;

	public RouterFunction<ServerResponse> serverResponseRouterFunction() {
		return RouterFunctions.route()
			.GET("/{a}/{b}", isOperation("+"), requestHandler::additionHandler)
			.GET("/{a}/{b}", isOperation("-"), requestHandler::subtractionHandler)
			.GET("/{a}/{b}", isOperation("*"), requestHandler::multiplicationHandler)
			.GET("/{a}/{b}", isOperation("/"), requestHandler::divisionHandler)
			.GET("/{a}/{b}", req -> ServerResponse.badRequest().bodyValue("Operation is not valid"))
			.build();
	}

	@Bean
	public RouterFunction<ServerResponse> highLevelCalculatorRouter() {
		return RouterFunctions.route()
			.path("/calculator", this::serverResponseRouterFunction)
			.build();
	}

	private RequestPredicate isOperation(String operation) {
		return RequestPredicates.headers(headers -> operation.equals(
				headers.asHttpHeaders().toSingleValueMap().get("Operation")
		));
	}

}
