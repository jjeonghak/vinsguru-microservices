package com.vinsguru.reactivekafkaplayground.sec11;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

/**
 * flatMap - parallel using groupBy
 */
@Slf4j
public class KafkaConsumer {

	public static void main(String[] args) {

		Map<String, Object> consumerConfig = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, "vinsguru-group",
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
				ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "1"
		);

		ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create(consumerConfig)
				.commitInterval(Duration.ofSeconds(1))
				.subscription(List.of("order-events"));

		KafkaReceiver.create(receiverOptions)
				.receive()
				// .groupBy(ConsumerRecord::partition)
				// .groupBy(record -> record.key().hashCode() % 5)
				.groupBy(record -> Long.parseLong(record.key()) % 5) //just for demo
				.flatMap(KafkaConsumer::batchProcess)
				.subscribe();

	}

	private static Mono<Void> batchProcess(GroupedFlux<Long, ReceiverRecord<String, String>> flux) {
		return flux
				.publishOn(Schedulers.boundedElastic()) //just for demo
				.doFirst(() -> log.info("------------------------------ mod: [{}]", flux.key()))
				.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value()))
				.doOnNext(record -> record.receiverOffset().acknowledge())
				.then(Mono.delay(Duration.ofSeconds(1)))
				.then();
	}

}
