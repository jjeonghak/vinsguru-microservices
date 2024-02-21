package com.vinsguru.reactivekafkaplayground.sec04;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.serialization.StringSerializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

/**
 * to produce records along with headers
 */
@Slf4j
public class KafkaProducer {

	public static void main(String[] args) {

		Map<String, Object> producerConfig = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
		);

		SenderOptions<String, String> senderOptions = SenderOptions.<String, String>create(producerConfig);

		Flux<SenderRecord<String, String, String>> flux = Flux.range(1, 100)
				.map(KafkaProducer::createSenderRecord);

		KafkaSender<String, String> sender = KafkaSender.create(senderOptions);

		sender.send(flux)
				.doOnNext(result -> log.info("correlation id [{}]", result.correlationMetadata()))
				.doOnComplete(sender::close)
				.subscribe();

	}

	private static SenderRecord<String, String, String> createSenderRecord(Integer id) {
		var headers = new RecordHeaders();
		headers.add("client-id", "some-client".getBytes());
		headers.add("tracing-id", "123".getBytes());

		var pr = new ProducerRecord<>("order-events", null, id.toString(), "order-" + id, headers);
		return SenderRecord.create(pr, pr.key());
	}

}
