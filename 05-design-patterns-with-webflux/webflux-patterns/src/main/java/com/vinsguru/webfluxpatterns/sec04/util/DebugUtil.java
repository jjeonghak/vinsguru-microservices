package com.vinsguru.webfluxpatterns.sec04.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinsguru.webfluxpatterns.sec04.dto.OrchestrationRequestContext;

public class DebugUtil {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static void print(String service, OrchestrationRequestContext ctx) {
		try {
			System.out.println(
					service + ": print ctx\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ctx)
			);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
