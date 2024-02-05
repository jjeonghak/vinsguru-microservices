package com.vinsguru.redisperformance.service.util;

import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;

import com.vinsguru.redisperformance.entity.Product;
import com.vinsguru.redisperformance.repository.ProductRepository;

import reactor.core.publisher.Mono;

@Service
public class ProductLocalCacheTemplate extends CacheTemplate<Long, Product> {
	private final ProductRepository repository;
	private final RLocalCachedMap<Long, Product> map;

	public ProductLocalCacheTemplate(RedissonClient client, ProductRepository repository) {
		this.repository = repository;

		LocalCachedMapOptions<Long, Product> options = LocalCachedMapOptions.<Long, Product>defaults()
				.syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
				.reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);

		this.map = client.getLocalCachedMap("product", new TypedJsonJacksonCodec(Long.class, Product.class), options);
	}

	@Override
	protected Mono<Product> getFromSource(Long key) {
		return this.repository.findById(key);
	}

	@Override
	protected Mono<Product> getFromCache(Long key) {
		return Mono.justOrEmpty(this.map.get(key));
	}

	@Override
	protected Mono<Product> updateSource(Long key, Product value) {
		return this.repository.findById(key)
				.doOnNext(p -> value.setId(key))
				.flatMap(p -> this.repository.save(value));
	}

	@Override
	protected Mono<Product> updateCache(Long key, Product value) {
		return Mono.create(sink ->
				this.map.fastPutAsync(key, value)
						.thenAccept(b -> sink.success(value))
						.exceptionally(ex -> {
								sink.error(ex);
								return null;
						})
		);
	}

	@Override
	protected Mono<Void> deleteFromSource(Long key) {
		return this.repository.deleteById(key);
	}

	@Override
	protected Mono<Void> deleteFromCache(Long key) {
		return Mono.create(sink ->
				this.map.fastRemoveAsync(key)
						.thenAccept(b -> sink.success())
						.exceptionally(ex -> {
								sink.error(ex);
								return null;
						})
		);
	}
}
