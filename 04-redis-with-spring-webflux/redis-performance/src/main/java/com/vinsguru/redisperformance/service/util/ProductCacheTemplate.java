package com.vinsguru.redisperformance.service.util;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;

import com.vinsguru.redisperformance.entity.Product;
import com.vinsguru.redisperformance.repository.ProductRepository;

import reactor.core.publisher.Mono;

// @Service
public class ProductCacheTemplate extends CacheTemplate<Long, Product> {

	private final ProductRepository repository;
	private final RMapReactive<Long, Product> map;

	public ProductCacheTemplate(RedissonReactiveClient client, ProductRepository repository) {
		this.repository = repository;
		this.map = client.getMap("product", new TypedJsonJacksonCodec(Long.class, Product.class));
	}

	@Override
	protected Mono<Product> getFromSource(Long key) {
		return this.repository.findById(key);
	}

	@Override
	protected Mono<Product> getFromCache(Long key) {
		return this.map.get(key);
	}

	@Override
	protected Mono<Product> updateSource(Long key, Product value) {
		return this.repository.findById(key)
				.doOnNext(p -> value.setId(key))
				.flatMap(p -> this.repository.save(value));
	}

	@Override
	protected Mono<Product> updateCache(Long key, Product value) {
		return this.map.fastPut(key, value)
				.doOnNext(b -> value.setId(key))
				.thenReturn(value);
	}

	@Override
	protected Mono<Void> deleteFromSource(Long key) {
		return this.repository.deleteById(key);
	}

	@Override
	protected Mono<Void> deleteFromCache(Long key) {
		return this.map.fastRemove(key).then();
	}
}
