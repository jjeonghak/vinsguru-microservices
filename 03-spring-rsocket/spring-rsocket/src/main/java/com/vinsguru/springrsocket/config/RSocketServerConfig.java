package com.vinsguru.springrsocket.config;

import java.time.Duration;

import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.rsocket.core.Resume;

@Configuration
public class RSocketServerConfig {

	@Bean
	public RSocketServerCustomizer customizer() {
		return c -> c.resume(resumeStrategy());
	}

	private Resume resumeStrategy() {
		return new Resume()
				.sessionDuration(Duration.ofMinutes(5));
	}

}
