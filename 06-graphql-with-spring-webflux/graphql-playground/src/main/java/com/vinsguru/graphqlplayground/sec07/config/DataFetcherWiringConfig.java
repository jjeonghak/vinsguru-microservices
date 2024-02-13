package com.vinsguru.graphqlplayground.sec07.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.vinsguru.graphqlplayground.sec07.service.CustomerOrderDataFetcher;

@Configuration
public class DataFetcherWiringConfig {

	@Autowired
	private CustomerOrderDataFetcher dataFetcher;

	@Bean
	public RuntimeWiringConfigurer configurer() {
		// return c -> c.type("Query", b -> b.dataFetchers(map()));
		return c -> c.type("Query", b -> b.dataFetcher("customers", dataFetcher));
	}

	// private Map<String, DataFetcher> map() {
	// 	return Map.of(
	// 			"customers", dfe -> "s",
	// 			"age", dfe -> 12,
	// 			"city", dfe -> "atlanta"
 	// 	);
	// }

}
