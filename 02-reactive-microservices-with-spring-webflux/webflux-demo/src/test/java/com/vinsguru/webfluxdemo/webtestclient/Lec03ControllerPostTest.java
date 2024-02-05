package com.vinsguru.webfluxdemo.webtestclient;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.vinsguru.webfluxdemo.controllers.ReactiveMathController;
import com.vinsguru.webfluxdemo.dtos.MultiplyRequestDto;
import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.services.ReactiveMathService;

import reactor.core.publisher.Mono;

@WebFluxTest(controllers = {ReactiveMathController.class})
public class Lec03ControllerPostTest {

	@Autowired
	private WebTestClient client;

	@MockBean
	private ReactiveMathService reactiveMathService;

	@Test
	public void postTest() {

		Mockito.when(reactiveMathService.multiply(Mockito.any()))
				.thenReturn(Mono.just(new Response(1, LocalDate.now())));

		client
				.post()
				.uri("/reactive/math/multiply")
				.accept(MediaType.APPLICATION_JSON)
				.headers(h -> h.setBasicAuth("username", "password"))
				.headers(h -> h.set("somekey", "somevalue"))
				.bodyValue(new MultiplyRequestDto())
				.exchange()
				.expectStatus().is2xxSuccessful();

	}

}
