package com.vinsguru.redisspring.city.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vinsguru.redisspring.city.client.CityClient;
import com.vinsguru.redisspring.city.dto.City;

import reactor.core.publisher.Mono;

@Service
public class CityService {

	private final CityClient cityClient;

	/**
	 * if you use Map Cache, you can set up ttl each element
	 */
	private final RMapReactive<String, City> cityMap;
	// private final RMapCacheReactive<String, City> cityMapCache;

	public CityService(RedissonReactiveClient client, CityClient cityClient) {
		this.cityClient = cityClient;
		this.cityMap = client.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
		// this.cityMapCache = client.getMapCache("city", new TypedJsonJacksonCodec(String.class, City.class));
	}

	/**
	 * get from cache
	 * if empty - get from db / source
	 * put it in cache
	 *
	 * public Mono<City> getCity(String zipCode) {
	 * 		return cityMap.get(zipCode)
	 * 			// return cityMapCache.get(zipCode)
	 * 			.switchIfEmpty(
	 * 				cityClient.getCity(zipCode)
	 * 					.flatMap(c -> cityMap.fastPut(zipCode, c).thenReturn(c))
	 * 				// .flatMap(c -> cityMapCache.fastPut(zipCode, c, 10, TimeUnit.SECONDS).thenReturn(c))
	 * 			);
	 * }
	 */

	public Mono<City> getCity(String zipCode) {
		return cityMap.get(zipCode)
				.onErrorResume(ex -> cityClient.getCity(zipCode));
	}

	@Scheduled(fixedRate = 10000)
	public void updateCity() {
		cityClient.getAll()
				.collectList()
				.map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
				.flatMap(cityMap::putAll)
				.subscribe();
	}

}
