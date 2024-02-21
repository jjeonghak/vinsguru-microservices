package com.vinsguru.reactivekafkaplayground.sec01;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Slf4j
public class KafkaMultipleConsumer {

	public static void main(String[] args) {

		Map<String, Object> consumerConfig = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, "vinsguru-group-2",
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
				ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "1"
		);

		ReceiverOptions<Object, Object> receiverOptions = ReceiverOptions.create(consumerConfig)
				.subscription(Pattern.compile("order.*"));

		KafkaReceiver.create(receiverOptions)
				.receive()
				.doOnNext(record -> log.info("topic [{}], key [{}], value [{}]", record.topic(), record.key(), record.value()))
				.doOnNext(record -> record.receiverOffset().acknowledge()) //add current offset
				.subscribe();

	}

}
