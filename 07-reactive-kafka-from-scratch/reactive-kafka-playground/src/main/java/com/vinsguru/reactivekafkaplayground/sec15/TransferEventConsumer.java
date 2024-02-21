package com.vinsguru.reactivekafkaplayground.sec15;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@Slf4j
public class TransferEventConsumer {

	private static final String REGEX = ",";
	private final KafkaReceiver<String, String> receiver;

	public TransferEventConsumer(KafkaReceiver<String, String> receiver) {
		this.receiver = receiver;
	}

	public Flux<TransferEvent> receive() {
		// return this.receiver.receiveAtmostOnce()
		return this.receiver.receive()
				.doOnNext(record -> log.info("key: [{}], value: [{}]", record.key(), record.value()))
				.map(this::toTransferEvent);
	}

	private TransferEvent toTransferEvent(ReceiverRecord<String, String> record) {
		var arr = record.value().split(REGEX);
		var runnable = record.key().equals("6") ? fail() : ack(record);

		return new TransferEvent(
				record.key(), arr[0], arr[1], arr[2], runnable
		);
	}

	private Runnable ack(ReceiverRecord<String, String> record) {
		return () -> record.receiverOffset().acknowledge();
	}

	private Runnable fail() {
		return () -> { throw new RuntimeException("error while acknowledge"); };
	}

}
