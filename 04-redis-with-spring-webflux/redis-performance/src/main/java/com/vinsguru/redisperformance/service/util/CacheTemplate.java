package com.vinsguru.redisperformance.service.util;

import reactor.core.publisher.Mono;

public abstract class CacheTemplate<K, V> {

	public Mono<V> get(K key) {
		return getFromCache(key)
				.switchIfEmpty(
						getFromSource(key)
								.flatMap(value -> updateCache(key, value))
				);
	}

	public Mono<V> update(K key, V value) {
		return updateSource(key, value)
				.flatMap(v -> deleteFromCache(key).thenReturn(v));
	}

	public Mono<Void> delete(K key) {
		return deleteFromSource(key)
				.then(deleteFromCache(key));
	}

	protected abstract Mono<V> getFromSource(K key);

	protected abstract Mono<V> getFromCache(K key);

	protected abstract Mono<V> updateSource(K key, V value);

	protected abstract Mono<V> updateCache(K key, V value);

	protected abstract Mono<Void> deleteFromSource(K key);

	protected abstract Mono<Void> deleteFromCache(K key);

}
