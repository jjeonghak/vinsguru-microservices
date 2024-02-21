package com.vinsguru.reactivekafkaplayground.sec15;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Slf4j
public class TransferDemo {

	public static void main(String[] args) {

		var transferEventConsumer = new TransferEventConsumer(kafkaReceiver());
		var transferEventProcessor = new TransferEventProcessor(kafkaSender());

		transferEventConsumer
				.receive()
				.transform(transferEventProcessor::process)
				.doOnNext(result -> log.info("transfer success: [{}]", result))
				.doOnError(ex -> log.error(ex.getMessage()))
				.subscribe();

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
				.subscription(List.of("transfer-requests"));

		return KafkaReceiver.create(receiverOptions);
	}

	private static KafkaSender<String, String> kafkaSender() {
		Map<String, Object> producerConfig = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.TRANSACTIONAL_ID_CONFIG, "money-transfer"
		);

		SenderOptions<String, String> senderOptions = SenderOptions.create(producerConfig);
		return KafkaSender.create(senderOptions);
	}

}
