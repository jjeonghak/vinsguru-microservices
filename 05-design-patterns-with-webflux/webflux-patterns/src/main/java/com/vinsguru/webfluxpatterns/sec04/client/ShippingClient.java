package com.vinsguru.webfluxpatterns.sec04.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec04.dto.ShippingRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.ShippingResponse;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;

import reactor.core.publisher.Mono;

@Service
public class ShippingClient {

	private static final String SCHEDULE = "/schedule";
	private static final String CANCEL = "/cancel";

	private final WebClient client;

	public ShippingClient(@Value("${sec04.service.shipping}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<ShippingResponse> schedule(ShippingRequest request) {
		return call(SCHEDULE, request);
	}

	public Mono<ShippingResponse> cancel(ShippingRequest request) {
		return call(CANCEL, request);
	}

	private Mono<ShippingResponse> call(String endPoint, ShippingRequest request) {
		return this.client
				.post()
				.uri(endPoint)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(ShippingResponse.class)
				.onErrorReturn(buildErrorResponse(request));
	}

	private ShippingResponse buildErrorResponse(ShippingRequest request) {
		return ShippingResponse.create(
				null,
				request.getQuantity(),
				Status.FAILED,
				null,
				null
		);
	}

}
