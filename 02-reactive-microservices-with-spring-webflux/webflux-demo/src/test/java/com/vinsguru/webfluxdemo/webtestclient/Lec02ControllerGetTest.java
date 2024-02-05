package com.vinsguru.webfluxdemo.webtestclient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.vinsguru.webfluxdemo.controllers.ParamsController;
import com.vinsguru.webfluxdemo.controllers.ReactiveMathController;
import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.services.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//this annotation do not create all beans, so target bean required
@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class Lec02ControllerGetTest {

	@Autowired
	private WebTestClient client;

	@MockBean
	private ReactiveMathService reactiveMathService;

	@Test
	public void singleResponseTest() {

		Flux<Response> flux = Flux.range(1, 3)
				.map(i -> new Response(i, LocalDate.now()));

		Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt()))
				.thenReturn(flux);

		client
				.get()
				.uri("/reactive/math/table/{number}", 5)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Response.class)
				.hasSize(3);

	}

	@Test
	public void streamResponseTest() {

		Flux<Response> flux = Flux.range(1, 3)
				.map(i -> new Response(i, LocalDate.now()))
				.delayElements(Duration.ofMillis(100));

		Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt()))
				.thenReturn(flux);

		client
				.get()
				.uri("/reactive/math/table/stream/{number}", 5)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
				.expectBodyList(Response.class)
				.hasSize(3);

	}

	@Test
	public void paramsTest() {

		Map<String, Integer> params = Map.of(
				"pageSize", 10,
				"pageNumber", 20
		);

		client
				.get()
				.uri(b -> b.path("/jobs/search").query("pageSize={pageSize}&pageNumber={pageNumber}").build(params))
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(Integer.class)
				.hasSize(2).contains(10, 20);
	}

}
