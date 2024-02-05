package com.vinsguru.webfluxpatterns.sec04.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.vinsguru.webfluxpatterns.sec04.dto.InventoryRequest;
import com.vinsguru.webfluxpatterns.sec04.dto.InventoryResponse;
import com.vinsguru.webfluxpatterns.sec04.dto.Status;

import reactor.core.publisher.Mono;

@Service
public class InventoryClient {

	private static final String DEDUCT = "/deduct";
	private static final String RESTORE = "/restore";

	private final WebClient client;

	public InventoryClient(@Value("${sec04.service.inventory}") String url) {
		this.client = WebClient.builder()
				.baseUrl(url)
				.build();
	}

	public Mono<InventoryResponse> deduct(InventoryRequest request) {
		return call(DEDUCT, request);
	}

	public Mono<InventoryResponse> restore(InventoryRequest request) {
		return call(RESTORE, request);
	}

	private Mono<InventoryResponse> call(String endPoint, InventoryRequest request) {
		return this.client
				.post()
				.uri(endPoint)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(InventoryResponse.class)
				.onErrorReturn(buildErrorResponse(request));
	}

	private InventoryResponse buildErrorResponse(InventoryRequest request) {
		return InventoryResponse.create(
				null,
				request.getProductId(),
				request.getQuantity(),
				null,
				Status.FAILED
		);
	}

}
