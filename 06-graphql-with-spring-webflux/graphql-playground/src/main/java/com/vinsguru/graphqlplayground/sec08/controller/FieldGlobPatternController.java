package com.vinsguru.graphqlplayground.sec08.controller;

import java.util.UUID;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import graphql.schema.DataFetchingFieldSelectionSet;

@Controller
public class FieldGlobPatternController {

	@QueryMapping("level1")
	public Object level1(DataFetchingFieldSelectionSet selectionSet) {
		System.out.println(selectionSet.contains("level2"));
		System.out.println(selectionSet.contains("level2/level3"));
		System.out.println(selectionSet.contains("*/level3"));
		System.out.println(selectionSet.contains("**/level5"));
		return UUID.randomUUID().toString();
	}

}
