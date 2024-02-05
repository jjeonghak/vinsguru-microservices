package com.vinsguru.redisspring.geo.service;

import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.vinsguru.redisspring.geo.dto.GeoLocation;
import com.vinsguru.redisspring.geo.dto.Restaurant;
import com.vinsguru.redisspring.geo.util.RestaurantUtil;

import reactor.core.publisher.Flux;

@Service
public class DataSetupService implements CommandLineRunner {

	private final RedissonReactiveClient client;
	private final RGeoReactive<Restaurant> geo;
	private final RMapReactive<String, GeoLocation> map;

	public DataSetupService(RedissonReactiveClient client) {
		this.client = client;
		this.geo = client.getGeo("restaurants", new TypedJsonJacksonCodec(Restaurant.class));
		this.map = client.getMap("us:texas", new TypedJsonJacksonCodec(String.class, GeoLocation.class));
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.fromIterable(RestaurantUtil.getRestaurants())
				.flatMap(r -> this.geo.add(r.getLongitude(), r.getLatitude(), r).thenReturn(r))
				.flatMap(r -> this.map.fastPut(r.getZip(), GeoLocation.of(r.getLongitude(), r.getLatitude())))
				.doFinally(s -> System.out.println("DataSetupService: restaurants added " + s))
				.subscribe();
	}

}
