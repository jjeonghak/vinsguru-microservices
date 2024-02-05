package com.vinsguru.redisspring.weather.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class ExternalService {

	@CachePut(value = "weather", key = "#zip")
	public int getWeatherInfo(int zip) {
		return ThreadLocalRandom.current().nextInt(60, 100);
	}

}
