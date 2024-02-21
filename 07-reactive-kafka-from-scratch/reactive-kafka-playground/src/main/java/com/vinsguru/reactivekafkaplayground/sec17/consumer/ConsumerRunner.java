package com.vinsguru.reactivekafkaplayground.sec17.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerRunner implements CommandLineRunner {


	private final ReactiveKafkaConsumerTemplate<String, DummyOrder> template;

	@Override
	public void run(String... args) throws Exception {
		this.template.receive()
				// .doOnNext(r -> r.headers().forEach(h -> log.info("header key [{}], header value [{}]", h.key(), new String(h.value()))))
				.doOnNext(r -> log.info("key [{}], value [{}]", r.key(), r.value()))
				.subscribe();
	}

}
