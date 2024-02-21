package com.vinsguru.reactivekafkaplayground;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.test.condition.EmbeddedKafkaCondition;
import org.springframework.kafka.test.context.EmbeddedKafka;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.test.StepVerifier;

@Slf4j
@EmbeddedKafka(
		// ports = 9092,
		partitions = 1,
		brokerProperties = { "auto.create.topics.enable=true" },
		topics = { "order-events" }
)
class EmbeddedKafkaPlaygroundTests {

	@Test
	void embeddedKafkaDemo() {

		// use random port
		var brokers = EmbeddedKafkaCondition.getBroker().getBrokersAsString();

		StepVerifier.create(Producer.run(brokers))
				.verifyComplete();

		StepVerifier.create(Consumer.run(brokers))
				.verifyComplete();

	}

	private static class Consumer {

		public static Mono<Void> run(String brokers) {
			Map<String, Object> consumerConfig = Map.of(
					ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers,
					ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
					ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
					ConsumerConfig.GROUP_ID_CONFIG, "vinsguru-group-1",
					ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
					ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "1"
			);

			ReceiverOptions<Object, Object> receiverOptions = ReceiverOptions.create(consumerConfig)
					.subscription(List.of("order-events"));

			return KafkaReceiver.create(receiverOptions)
					.receive()
					.take(10)
					.doOnNext(record -> log.info("key [{}], value [{}]", record.key(), record.value()))
					.doOnNext(record -> record.receiverOffset().acknowledge()) //add current offset
					.then();
		}

	}

	private static class Producer {

		public static Mono<Void> run(String brokers) {
			Map<String, Object> producerConfig = Map.of(
					ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers,
					ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
					ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
			);

			SenderOptions<String, String> senderOptions = SenderOptions.create(producerConfig);

			Flux<SenderRecord<String, String, String>> flux = Flux.range(1, 10)
					.delayElements(Duration.ofMillis(10))
					.map(i -> new ProducerRecord<>("order-events", i.toString(), "order-" + i))
					.map(pr -> SenderRecord.create(pr, pr.key()));

			KafkaSender<String, String> sender = KafkaSender.create(senderOptions);

			return sender.send(flux)
					.doOnNext(result -> log.info("correlation id [{}]", result.correlationMetadata()))
					.doOnComplete(sender::close)
					.then();
		}

	}

}
