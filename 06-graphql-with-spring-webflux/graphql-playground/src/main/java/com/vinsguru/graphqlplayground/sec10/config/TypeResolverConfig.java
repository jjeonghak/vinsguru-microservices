package com.vinsguru.graphqlplayground.sec10.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ClassNameTypeResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.vinsguru.graphqlplayground.sec10.dto.Book;
import com.vinsguru.graphqlplayground.sec10.dto.Electronics;
import com.vinsguru.graphqlplayground.sec10.dto.Fruit;

import graphql.schema.TypeResolver;

@Configuration
public class TypeResolverConfig {

	@Bean
	public RuntimeWiringConfigurer configurer(TypeResolver resolver) {
		return c -> c.type("Product", b -> b.typeResolver(resolver));
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
