package com.vinsguru.reactivekafkaplayground.sec12;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

/**
 * error handling demo: processing pipeline
 */
@Slf4j
public class KafkaConsumerV2 {

	public static void main(String[] args) {

		Map<String, Object> consumerConfig = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, "vinsguru-group",
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
				ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "1"
		);

		ReceiverOptions<Object, Object> receiverOptions = ReceiverOptions.create(consumerConfig)
				.subscription(List.of("order-events"));

		KafkaReceiver.create(receiverOptions)
				.receive()
				.log()
				.concatMap(KafkaConsumerV2::process)
				.subscribe();

	}

	private static Mono<Void> process(ReceiverRecord<Object, Object> receiverRecord) {
		return Mono.just(receiverRecord)
				.doOnNext(record -> {
						var index = ThreadLocalRandom.current().nextInt(1, 10);
						log.info("key [{}], value [{}], index [{}]", record.key(), record.value().toString().toCharArray()[index], index);
				})
				.retryWhen(
						Retry.fixedDelay(3, Duration.ofSeconds(1))
								.onRetryExhaustedThrow((spec, signal) -> signal.failure())
				)
				.doOnError(ex -> log.error(ex.getMessage()))
				.doFinally(s -> receiverRecord.receiverOffset().acknowledge())
				.onErrorComplete()
				.then();
	}

}
