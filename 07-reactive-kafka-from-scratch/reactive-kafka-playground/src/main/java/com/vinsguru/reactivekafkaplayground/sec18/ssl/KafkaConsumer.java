package com.vinsguru.reactivekafkaplayground.sec18.ssl;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

/**
 * to demo a simple kafka consumer using SASL SSL
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

				// setup SASL security
				SaslConfigs.SASL_MECHANISM, "PLAIN",
				CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT",
				SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required serviceName=\"Kafka\" username=\"client\" password=\"client-secret\";"
		);

		ReceiverOptions<Object, Object> receiverOptions = ReceiverOptions.create(consumerConfig)
				.subscription(List.of("order-events"));

		KafkaReceiver.create(receiverOptions)
				.receive()
				.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value()))
				.doOnNext(record -> record.receiverOffset().acknowledge()) //add current offset
				.subscribe();

	}

}
