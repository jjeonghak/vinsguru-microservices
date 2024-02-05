package com.vinsguru.webfluxdemo.webtestclient;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.vinsguru.webfluxdemo.controllers.ReactiveMathValidationController;
import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.services.ReactiveMathService;

import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathValidationController.class)
public class Lec04ErrorHandlingTest {

	@Autowired
	private WebTestClient client;

	@MockBean
	private ReactiveMathService reactiveMathService;

	@Test
	public void errorHandlingTest() {

		Mockito.when(reactiveMathService.findSquare(Mockito.anyInt()))
				.thenReturn(Mono.just(new Response(1, LocalDate.now())));

		client
				.get()
				.uri("/reactive/math/square/throw/{number}", 5)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$.message").isEqualTo("Allowed range is 10 - 20")
				.jsonPath("$.errorCode").isEqualTo(100)
				.jsonPath("$.input").isEqualTo(5);

	}

}
