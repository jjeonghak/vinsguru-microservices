package com.vinsguru.redisson;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.GeoUnit;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.api.geo.OptionalGeoSearch;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redisson.dto.GeoLocation;
import com.vinsguru.redisson.dto.Restaurant;
import com.vinsguru.redisson.util.RestaurantUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec17GeoSpatialTest extends BaseTest {

	private RGeoReactive<Restaurant> geo;
	private RMapReactive<String, GeoLocation> map;

	@BeforeAll
	public void setGeo() {
		this.geo = this.client.getGeo("restaurants", new TypedJsonJacksonCodec(Restaurant.class));
		this.map = this.client.getMap("us:texas", new TypedJsonJacksonCodec(String.class, GeoLocation.class));
	}

	@Test
	public void add() {

		Mono<Void> setGeo = Flux.fromIterable(RestaurantUtil.getRestaurants())
				.flatMap(r -> this.geo.add(r.getLongitude(), r.getLatitude(), r).thenReturn(r))
				.flatMap(r -> this.map.fastPut(r.getZip(), GeoLocation.of(r.getLongitude(), r.getLatitude())))
				.then();

		StepVerifier.create(setGeo)
				.verifyComplete();

	}

	@Test
	public void search() {
		Mono<Void> search = this.map.get("75247")
				.map(gl -> GeoSearchArgs.from(gl.getLongitude(), gl.getLatitude()).radius(5, GeoUnit.MILES))
				.flatMap(r -> this.geo.search(r))
				.flatMapIterable(Function.identity())
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(search)
				.verifyComplete();

	}

}
