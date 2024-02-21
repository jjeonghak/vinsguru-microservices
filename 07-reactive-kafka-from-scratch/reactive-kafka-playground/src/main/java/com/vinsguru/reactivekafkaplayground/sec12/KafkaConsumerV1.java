package com.vinsguru.reactivekafkaplayground.sec12;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.util.retry.Retry;

/**
 * error handling demo: a simple processing issue
 */
@Slf4j
public class KafkaConsumerV1 {

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
				.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value().toString().toCharArray()[15])) //just for demo
				.doOnError(ex -> log.error(ex.getMessage()))
				.doOnNext(record -> record.receiverOffset().acknowledge())
				.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
				.blockLast(); //just for demo

	}

}
