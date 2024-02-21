package com.vinsguru.reactivekafkaplayground.sec13;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.util.retry.Retry;

/**
 * error handling demo: dead letter topic
 */
@Slf4j
public class KafkaConsumer {

	public static void main(String[] args) {

		var dltProducer = deadLetterTopicProducer();
		var processor = new OrderEventProcessor(dltProducer);
		var receiver = kafkaReceiver();

		receiver.receive()
				.concatMap(processor::process)
				.subscribe();

	}

	private static ReactiveDeadLetterTopicProducer<String, String> deadLetterTopicProducer() {
		Map<String, Object> producerConfig = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
		);

		SenderOptions<String, String> senderOptions = SenderOptions.create(producerConfig);
		KafkaSender<String, String> sender = KafkaSender.create(senderOptions);

		return new ReactiveDeadLetterTopicProducer<>(
				sender,
				Retry.fixedDelay(2, Duration.ofSeconds(1))
		);
	}

	private static KafkaReceiver<String, String> kafkaReceiver() {
		Map<String, Object> consumerConfig = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, "vinsguru-group",
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
				ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "1"
		);

		ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create(consumerConfig)
				.subscription(List.of("order-events", "order-events-dlt"));

		return KafkaReceiver.create(receiverOptions);
	}

}
