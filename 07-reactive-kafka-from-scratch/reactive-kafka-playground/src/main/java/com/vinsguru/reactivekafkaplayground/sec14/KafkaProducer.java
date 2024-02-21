package com.vinsguru.reactivekafkaplayground.sec14;

import java.time.Duration;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

/**
 * to demo poison pill messages
 */
@Slf4j
public class KafkaProducer {

	public static void main(String[] args) {

		Map<String, Object> producerConfig = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class
		);

		SenderOptions<String, Integer> senderOptions = SenderOptions.create(producerConfig);

		Flux<SenderRecord<String, Integer, String>> flux = Flux.range(1, 10_000)
				.delayElements(Duration.ofSeconds(5))
				.map(i -> new ProducerRecord<>("order-events", i.toString(), i))
				.map(pr -> SenderRecord.create(pr, pr.key()));

		KafkaSender<String, Integer> sender = KafkaSender.create(senderOptions);

		sender.send(flux)
				.doOnNext(result -> log.info("correlation id [{}]", result.correlationMetadata()))
				.doOnComplete(sender::close)
				.subscribe();

	}

}
