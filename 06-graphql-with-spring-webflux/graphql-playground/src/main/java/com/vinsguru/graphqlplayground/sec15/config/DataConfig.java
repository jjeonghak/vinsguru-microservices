package com.vinsguru.graphqlplayground.sec15.config;

import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DataConfig {

	@Bean
	public ConnectionFactory initConnectionFactory() {
		return ConnectionFactoryBuilder
				.withUrl("r2dbcs:mysql://root:qwer1234@127.0.0.1:3306/local?serverZoneId=Asia/Seoul")
				.build();
	}

}
