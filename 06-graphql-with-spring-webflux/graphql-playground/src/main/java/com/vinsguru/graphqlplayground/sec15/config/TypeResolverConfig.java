package com.vinsguru.graphqlplayground.sec15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.vinsguru.graphqlplayground.sec15.dto.CustomerDto;
import com.vinsguru.graphqlplayground.sec15.dto.CustomerNotFound;

import graphql.schema.TypeResolver;

@Configuration
public class TypeResolverConfig {

	@Bean
	public RuntimeWiringConfigurer configurer(TypeResolver resolver) {
		return c -> c.type("CustomerResponse", b -> b.typeResolver(resolver));
	}

	@Bean
	public TypeResolver typeResolver() {
		ClassNameTypeResolver resolver = new ClassNameTypeResolver();
		resolver.addMapping(CustomerDto.class, "Customer");
		resolver.addMapping(CustomerNotFound.class, "CustomerNotFound");
		return resolver;
	}

}
