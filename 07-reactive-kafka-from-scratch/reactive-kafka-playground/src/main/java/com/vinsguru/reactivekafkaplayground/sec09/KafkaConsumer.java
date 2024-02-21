package com.vinsguru.reactivekafkaplayground.sec09;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

/**
 * receiveAutoAck with concatMap
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
				ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "1",
				ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "3"
		);

		ReceiverOptions<Object, Object> receiverOptions = ReceiverOptions.create(consumerConfig)
				.commitInterval(Duration.ofSeconds(1))
				.subscription(List.of("order-events"));

		KafkaReceiver.create(receiverOptions)
				.receiveAutoAck()
				.log()
				.concatMap(KafkaConsumer::batchProcess)
				.subscribe();

	}

	private static Mono<Void> batchProcess(Flux<ConsumerRecord<Object, Object>> flux) {
		return flux
				.doFirst(() -> log.info("------------------------------"))
				.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value()))
				.then(Mono.delay(Duration.ofSeconds(1)))
				.then();
	}

}
