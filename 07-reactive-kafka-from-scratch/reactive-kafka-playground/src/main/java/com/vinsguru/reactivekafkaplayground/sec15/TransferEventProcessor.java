package com.vinsguru.reactivekafkaplayground.sec15;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

import org.apache.kafka.clients.producer.ProducerRecord;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@Slf4j
public class TransferEventProcessor {

	private final KafkaSender<String, String> sender;

	public TransferEventProcessor(KafkaSender<String, String> sender) {
		this.sender = sender;
	}

	public Flux<List<SenderResult<String>>> process(Flux<TransferEvent> events) {
		return events.concatMap(this::validate)
				.concatMap(this::sendTransaction);
	}

	private Mono<List<SenderResult<String>>> sendTransaction(TransferEvent event) {
		var senderRecord = this.toSenderRecords(event);
		var manager = this.sender.transactionManager();

		return manager.begin()
				.then(
						this.sender.send(senderRecord)
								.delayElements(Duration.ofSeconds(1))
								.concatWith(Mono.fromRunnable(event.acknowledge()))
								.concatWith(manager.commit())
								.collectList()
				)
				.doOnError(ex -> log.error(ex.getMessage()))
				.onErrorResume(ex -> manager.abort());
	}

	// 5 does not have money to transfer
	private Mono<TransferEvent> validate(TransferEvent event) {
		return Mono.just(event)
				.filter(Predicate.not(e -> e.key().equals("5")))
				.switchIfEmpty(
						Mono.<TransferEvent>fromRunnable(event.acknowledge())
								.doFirst(() -> log.info("fails validation: [{}]", event.key()))
				);
	}

	private Flux<SenderRecord<String, String, String>> toSenderRecords(TransferEvent event) {
		var pr1 = new ProducerRecord<>("transaction-events", event.key(), "%s+%s".formatted(event.to(), event.amount()));
		var pr2 = new ProducerRecord<>("transaction-events", event.key(), "%s-%s".formatted(event.from(), event.amount()));
		var sr1 = SenderRecord.create(pr1, pr1.key());
		var sr2 = SenderRecord.create(pr2, pr2.key());

		return Flux.just(sr1, sr2);
	}

}
