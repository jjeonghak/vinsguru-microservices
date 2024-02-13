package com.vinsguru.graphqlplayground.sec11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.vinsguru.graphqlplayground.sec11.dto.Book;
import com.vinsguru.graphqlplayground.sec11.dto.Electronics;
import com.vinsguru.graphqlplayground.sec11.dto.Fruit;

import graphql.schema.TypeResolver;

@Configuration
public class TypeResolverConfig {

	@Bean
	public RuntimeWiringConfigurer configurer(TypeResolver resolver) {
		return c -> c.type("Result", b -> b.typeResolver(resolver));
	}

	@Bean
	public TypeResolver typeResolver() {
		ClassNameTypeResolver resolver = new ClassNameTypeResolver();
		resolver.addMapping(Fruit.class, "Fruit");
		resolver.addMapping(Electronics.class, "Electronics");
		resolver.addMapping(Book.class, "Book");
		return resolver;
	}

}
