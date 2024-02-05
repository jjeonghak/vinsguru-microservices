package com.vinsguru.redisspring.weather.service;

import java.util.stream.IntStream;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {

	private final ExternalService client;

	@Cacheable(value = "weather")
	public int getInfo(int zip) {
		return 0;
	}

	@Scheduled(fixedRate = 10000)
	public void update() {
		System.out.println("WeatherService: update weather");
		IntStream.rangeClosed(1, 5)
				.forEach(client::getWeatherInfo);
	}

}
