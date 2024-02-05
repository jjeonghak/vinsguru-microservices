package com.vinsguru.redisspring.geo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinsguru.redisspring.geo.dto.Restaurant;

public class RestaurantUtil {

	public static List<Restaurant> getRestaurants() {
		ObjectMapper mapper = new ObjectMapper();
		InputStream stream = RestaurantUtil.class.getClassLoader().getResourceAsStream("restaurant.json");
		try {
			return mapper.readValue(stream, new TypeReference<List<Restaurant>>() {});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
