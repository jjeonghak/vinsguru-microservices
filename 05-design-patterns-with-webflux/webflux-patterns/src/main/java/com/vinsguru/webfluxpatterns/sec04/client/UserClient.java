package com.vinsguru.webfluxpatterns.sec04.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec04.dto.PaymentRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.PaymentResponse;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;

import reactor.core.publisher.Mono;

@Service
public class UserClient {

	private static final String DEDUCT = "/deduct";
	private static final String REFUND = "/refund";

	private final WebClient client;

	public UserClient(@Value("${sec04.service.user}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<PaymentResponse> deduct(PaymentRequest request) {
		return call(DEDUCT, request);
	}

	public Mono<PaymentResponse> refund(PaymentRequest request) {
		return call(REFUND, request);
	}

	private Mono<PaymentResponse> call(String endPoint, PaymentRequest request) {
		return this.client
				.post()
				.uri(endPoint)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(PaymentResponse.class)
				.onErrorReturn(buildErrorResponse(request));
	}

	private PaymentResponse buildErrorResponse(PaymentRequest request) {
		return PaymentResponse.create(
				null,
				request.getUserId(),
				null,
				null,
				Status.FAILED
		);
	}

}
