package com.vinsguru.reactivekafkaplayground.sec16;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerRunner implements CommandLineRunner {

	private final ReactiveKafkaProducerTemplate<String, OrderEvent> template;

	@Override
	public void run(String... args) throws Exception {
		this.orders()
				.flatMap(e -> this.template.send("order-events", e.orderId().toString(), e))
				.doOnNext(r -> log.info("result [{}]", r.recordMetadata()))
				.subscribe();
	}

	private Flux<OrderEvent> orders() {
		return Flux.interval(Duration.ofMillis(500))
				.take(1000)
				.map(i -> new OrderEvent(UUID.randomUUID(), i, LocalDateTime.now()));
	}

}
