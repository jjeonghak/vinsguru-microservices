package com.vinsguru.reactivekafkaplayground.sec14;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

/**
 * to demo poison pill messages
 */
@Slf4j
public class KafkaConsumer {

	public static void main(String[] args) {

		Map<String, Object> consumerConfig = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, "vinsguru-group",
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
				ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "1"
		);

		ReceiverOptions<String, Integer> receiverOptions = ReceiverOptions.<String, Integer>create(consumerConfig)
				.withValueDeserializer(errorHandlingDeserializer())
				.subscription(List.of("order-events"));

		KafkaReceiver.create(receiverOptions)
				.receive()
				.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value()))
				.doOnNext(record -> record.receiverOffset().acknowledge())
				.subscribe();

	}

	private static ErrorHandlingDeserializer<Integer> errorHandlingDeserializer() {
		ErrorHandlingDeserializer<Integer> deserializer = new ErrorHandlingDeserializer<>(new IntegerDeserializer());
		deserializer.setFailedDeserializationFunction(info -> {
				log.error("failed record: {}", new String(info.getData()));
				return Integer.MIN_VALUE;
		});
		return deserializer;
	}

}
