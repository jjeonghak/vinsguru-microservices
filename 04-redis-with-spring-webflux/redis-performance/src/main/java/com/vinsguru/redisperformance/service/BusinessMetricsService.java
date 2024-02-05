package com.vinsguru.redisperformance.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.redisson.api.RScoredSortedSetReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BusinessMetricsService {

	private final RedissonReactiveClient client;

	public Mono<Map<Long, Double>> topProducts(int n) {
		if (n < 0) {
			throw new RuntimeException("BusinessMetricsService: invalid top n " + n);
		}

		String format = DateTimeFormatter.ofPattern("YYYYMMdd").format(LocalDate.now());
		RScoredSortedSetReactive<Long> set = client.getScoredSortedSet("product:visit:" + format, LongCodec.INSTANCE);

		return set.entryRangeReversed(0, n - 1)
				.map(listSe -> listSe.stream().collect(
						Collectors.toMap(
								ScoredEntry::getValue,
								ScoredEntry::getScore,
								(a, b) -> a,
								LinkedHashMap::new
						)
				));

	}

}
