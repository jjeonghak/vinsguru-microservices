package com.vinsguru.webfluxpatterns.sec08.config;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType.*;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;

@Configuration
public class CircuitBreakerConfig {

	// @Bean
	// public CircuitBreakerConfigCustomizer reviewService() {
	// 	return CircuitBreakerConfigCustomizer.of("review-service", builder -> {
	// 			builder
	// 					.slidingWindowType(COUNT_BASED)
	// 					.slidingWindowSize(4)
	// 					.minimumNumberOfCalls(2)
	// 					.failureRateThreshold(50)
	// 					.waitDurationInOpenState(Duration.ofSeconds(10))
	// 					.permittedNumberOfCallsInHalfOpenState(2)
	// 					.recordExceptions(WebClientResponseException.class, TimeoutException.class)
	// 					.build();
	// 	});
	// }

}
