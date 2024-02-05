package com.vinsguru.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DatabaseConfig {

	@Bean
	public ConnectionFactory initConnectionFactory() {
		// ConnectionFactoryOptions options = builder()
		// 		.option(DRIVER, "mysql")
		// 		.option(HOST, "localhost")
		// 		.option(PORT, 3306)
		// 		.option(USER, "root")
		// 		.option(PASSWORD, "qwer1234")
		// 		.option(DATABASE, "local")
		// 		.build();

		return ConnectionFactories.get(
				"r2dbcs:mysql://root:qwer1234@127.0.0.1:3306/local?serverZoneId=Asia/Seoul"
		);
	}

	// @Bean
	// public MySqlConnectionConfiguration mySqlConnectionConfiguration() {
	// 	return MySqlConnectionConfiguration.builder()
	// 			.host("127.0.0.1")
	// 			.port(3306)
	// 			.user("root")
	// 			.password("qwer1234")
	// 			.database("local")
	// 			.serverZoneId(ZoneId.of("Asia/Seoul"))
	// 			.build();
	// }

}
