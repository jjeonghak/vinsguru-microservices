package com.vinsguru.productservice.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import com.vinsguru.productservice.event.ProductViewEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@RequiredArgsConstructor
public class ProductViewEventProducer {

	private final ReactiveKafkaProducerTemplate<String, ProductViewEvent> template;
	private final Sinks.Many<ProductViewEvent> sink;
	private final Flux<ProductViewEvent> flux;
	private final String topic;

	public void subscribe() {
		var sr = this.flux
				.map(e -> new ProducerRecord<>(topic, e.getProductId().toString(), e))
				.map(pr -> SenderRecord.create(pr, pr.key()));

		this.template.send(sr)
				.doOnNext(r -> log.info("ProductViewEventProducer: emitted event [{}]", r.correlationMetadata()))
				.subscribe();
	}

	public void emitEvent(ProductViewEvent event) {
		this.sink.tryEmitNext(event);
	}

}
