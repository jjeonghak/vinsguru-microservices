package com.vinsguru.reactivekafkaplayground;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;

import com.vinsguru.reactivekafkaplayground.sec17.consumer.DummyOrder;
import com.vinsguru.reactivekafkaplayground.sec17.producer.OrderEvent;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.test.StepVerifier;

@Slf4j
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource(properties = "app=consumer")
public class OrderEventConsumerTest extends AbstractIntegrationTest {

	@Test
	public void consumerTest(CapturedOutput output) {

		KafkaSender<String, OrderEvent> sender = createSender();
		var uuid = UUID.randomUUID();
		var orderEvent = new OrderEvent(uuid, 1L, LocalDateTime.now());
		var dummyOrder = new DummyOrder(uuid.toString(), "1");
		var sr = toSenderRecord("order-events", "1", orderEvent);

		var send = sender.send(Mono.just(sr))
				.then(Mono.delay(Duration.ofMillis(500)))
				.then();

		StepVerifier.create(send)
				.verifyComplete();

		Assertions.assertTrue(output.getOut().contains(dummyOrder.toString()));

	}

}
