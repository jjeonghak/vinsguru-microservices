package com.vinsguru.rsocket.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;

public class ObjectUtil {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static Payload toPayload(Object o) {
		try {
			byte[] bytes = mapper.writeValueAsBytes(o);
			return DefaultPayload.create(bytes);
		} catch (JsonProcessingException e) {
			System.out.println("ObjectUtil: failed writing payload by " + o);
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObject(Payload payload, Class<T> type) {
		try {
			byte[] bytes = payload.getData().array();
			return mapper.readValue(bytes, type);
		} catch (IOException e) {
			System.out.println("ObjectUtil: failed type casting from " + payload + " to " + type);
			throw new RuntimeException(e);
		}
	}

}
