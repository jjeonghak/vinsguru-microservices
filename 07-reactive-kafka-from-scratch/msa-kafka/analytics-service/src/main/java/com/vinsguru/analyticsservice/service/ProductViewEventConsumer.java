package com.vinsguru.analyticsservice.service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.vinsguru.analyticsservice.entity.ProductViewCount;
import com.vinsguru.analyticsservice.event.ProductViewEvent;
import com.vinsguru.analyticsservice.repository.ProductViewRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.kafka.receiver.ReceiverRecord;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductViewEventConsumer {

	private final ReactiveKafkaConsumerTemplate<String, ProductViewEvent> template;
	private final ProductViewRepository repository;
	private final Sinks.Many<Long> sink = Sinks.many().unicast().onBackpressureBuffer();
	private final Flux<Long> flux = sink.asFlux();

	@PostConstruct
	public void subscribe() {
		this.template
				.receive()
				.bufferTimeout(1000, Duration.ofSeconds(1))
				.flatMap(this::process)
				.subscribe();
	}

	public Flux<Long> companionFlux() {
		return this.flux;
	}

	private Mono<Void> process(List<ReceiverRecord<String, ProductViewEvent>> events) {
		var eventMap = events.stream()
				.map(r -> r.value().getProductId())
				.collect(Collectors.groupingBy(
						Function.identity(),
						Collectors.counting())
				);

		return this.repository.findAllById(eventMap.keySet())
				.collectMap(ProductViewCount::getId)
				.defaultIfEmpty(Collections.emptyMap())
				.map(dbMap -> eventMap.keySet().stream().map(productId -> updateViewCount(dbMap, eventMap, productId)).collect(Collectors.toList()))
				.flatMapMany(this.repository::saveAll)
				.doOnComplete(() -> events.get(events.size() - 1).receiverOffset().acknowledge())
				.doOnComplete(() -> sink.tryEmitNext(1L))
				.doOnError(ex -> log.error(ex.getMessage()))
				.then();
	}

	private ProductViewCount updateViewCount(
			Map<Long, ProductViewCount> dbMap,
			Map<Long, Long> eventMap,
			long productId) {
		var pvc = dbMap.getOrDefault(productId, new ProductViewCount(productId, 0L, true));
		pvc.setCount(pvc.getCount() + eventMap.get(productId));
		return pvc;
	}

}
