package com.vinsguru.reactivekafkaplayground;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.vinsguru.reactivekafkaplayground.sec17.producer.OrderEvent;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.test.StepVerifier;

@Slf4j
@TestPropertySource(properties = "app=producer")
public class OrderEventProducerTest extends AbstractIntegrationTest {

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void producerTest1() {

		KafkaReceiver<String, OrderEvent> receiver = createReceiver("order-events");
		var orderEvents = receiver.receive()
				.take(10)
				.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value()));

		StepVerifier.create(orderEvents)
				.consumeNextWith(r -> Assertions.assertNotNull(r.value().orderId()))
				.expectNextCount(9)
				.expectComplete()
				.verify(Duration.ofSeconds(10));

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void producerTest2() {

		KafkaReceiver<String, OrderEvent> receiver = createReceiver("order-events");
		var orderEvents = receiver.receive()
				.take(10)
				.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value()));

		StepVerifier.create(orderEvents)
				.consumeNextWith(r -> Assertions.assertNotNull(r.value().orderId()))
				.expectNextCount(9)
				.expectComplete()
				.verify(Duration.ofSeconds(10));

	}

}
