package com.vinsguru.reactivekafkaplayground.sec02;

import java.time.Duration;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

/**
 * to demo a simple kafka producer using reactor kafka
 * producer --> kafka broker <--> consumer
 *
 * apache.kafka -> KafkaProducer, ProducerRecord
 * reactor.kafka -> KafkaSender, SenderRecord
 */
@Slf4j
public class KafkaProducer {

	public static void main(String[] args) {

		Map<String, Object> producerConfig = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
		);

		SenderOptions<String, String> senderOptions = SenderOptions.create(producerConfig);

		Flux<SenderRecord<String, String, String>> flux = Flux.interval(Duration.ofMillis(100))
				.take(100)
				.map(i -> new ProducerRecord<>("order-events", i.toString(), "order-" + i))
				.map(pr -> SenderRecord.create(pr, pr.key()));

		KafkaSender<String, String> sender = KafkaSender.create(senderOptions);

		sender.send(flux)
				.doOnNext(result -> log.info("correlation id [{}]", result.correlationMetadata()))
				.doOnComplete(sender::close)
				.subscribe();

	}

}
