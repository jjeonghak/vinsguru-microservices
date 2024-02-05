package com.vinsguru.webfluxdemo.webtestclient;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vinsguru.webfluxdemo.configs.RequestHandler;
import com.vinsguru.webfluxdemo.configs.RouterConfig;
import com.vinsguru.webfluxdemo.dtos.Response;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctionTest {

	private WebTestClient client;

	@MockBean
	private RequestHandler handler;

	// @Autowired
	// private RouterConfig config;
	//
	// @BeforeAll
	// public void setClient() {
	// 	this.client = WebTestClient.bindToRouterFunction(config.highLevelRouter()).build();
	// }

	@Autowired
	private ApplicationContext ctx;

	@BeforeAll
	public void setClient() {
		this.client = WebTestClient.bindToApplicationContext(ctx).build();
		// WebTestClient.bindToServer().baseUrl("https://localhost:8080").build()
	}

	@Test
	public void test() {

		Mockito.when(handler.squareHandler(Mockito.any()))
				.thenReturn(ServerResponse.ok().bodyValue(new Response(1, LocalDate.now())));

		client
				.get()
				.uri("/router/math/square/{input}", 15)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Response.class)
				.value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(1));

	}


}
