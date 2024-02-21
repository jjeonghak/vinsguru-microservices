package com.vinsguru.reactivekafkaplayground.sec13;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

@Slf4j
public class OrderEventProcessor {

	private final ReactiveDeadLetterTopicProducer<String, String> deadLetterTopicProducer;

	public OrderEventProcessor(ReactiveDeadLetterTopicProducer<String, String> deadLetterTopicProducer) {
		this.deadLetterTopicProducer = deadLetterTopicProducer;
	}

	public Mono<Void> process(ReceiverRecord<String, String> receiverRecord) {
		return Mono.just(receiverRecord)
				.doOnNext(record -> {
						// if (record.key().endsWith("5")) {
						// 	throw new RuntimeException("processing exception");
						// }
						log.info("key [{}], value [{}]", record.key(), record.value());
						record.receiverOffset().acknowledge();
				})
				.onErrorMap(ex -> new RecordProcessingException(receiverRecord, ex))
				.transform(this.deadLetterTopicProducer.recordProcessingErrorHandler());
	}

}
