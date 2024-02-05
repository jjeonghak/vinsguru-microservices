package com.vinsguru.redisspring.geo.service;

import java.util.function.Function;

import org.redisson.api.GeoUnit;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;

import com.vinsguru.redisspring.geo.dto.GeoLocation;
import com.vinsguru.redisspring.geo.dto.Restaurant;

import reactor.core.publisher.Flux;

@Service

public class RestaurantLocatorService {

	private final RGeoReactive<Restaurant> geo;
	private final RMapReactive<String, GeoLocation> map;

	public RestaurantLocatorService(RedissonReactiveClient client) {
		this.geo = client.getGeo("restaurants", new TypedJsonJacksonCodec(Restaurant.class));
		this.map = client.getMap("us:texas", new TypedJsonJacksonCodec(String.class, GeoLocation.class));
	}

	public Flux<Restaurant> getRestaurants(String zipcode) {
		return this.map.get(zipcode)
				.map(gl -> GeoSearchArgs.from(gl.getLongitude(), gl.getLatitude()).radius(10, GeoUnit.MILES))
				.flatMap(this.geo::search)
				.flatMapIterable(Function.identity());
	}

}
