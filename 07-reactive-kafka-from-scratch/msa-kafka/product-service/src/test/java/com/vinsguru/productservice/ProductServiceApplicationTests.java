package com.vinsguru.productservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.vinsguru.productservice.event.ProductViewEvent;

import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
class ProductServiceApplicationTests extends AbstractIntegrationTest {

	@Autowired
	private WebTestClient client;

	@Test
	void productViewAndEventTest() {

		// view products
		viewProductSuccess(1L);
		viewProductSuccess(89L);
		viewProductError(1000L);
		viewProductSuccess(5L);

		// check if the events are emitted
		var events = this.<ProductViewEvent>createReceiver(PRODUCT_VIEW_EVENTS)
				.receive()
				.take(3);

		StepVerifier.create(events)
				.consumeNextWith(r -> Assertions.assertEquals(1, r.value().getProductId()))
				.consumeNextWith(r -> Assertions.assertEquals(89, r.value().getProductId()))
				.consumeNextWith(r -> Assertions.assertEquals(5, r.value().getProductId()))
				.verifyComplete();

	}

	private void viewProductSuccess(long id) {
		this.client
				.get()
				.uri("/product/{productId}", id)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody()
				.jsonPath("$.id").isEqualTo(id)
				.jsonPath("$.description").isEqualTo("product-" + id)
				.jsonPath("$.price").isEqualTo(id);
	}

	private void viewProductError(long id) {
		this.client
				.get()
				.uri("/product/{productId}", id)
				.exchange()
				.expectStatus().is4xxClientError();
	}

}
