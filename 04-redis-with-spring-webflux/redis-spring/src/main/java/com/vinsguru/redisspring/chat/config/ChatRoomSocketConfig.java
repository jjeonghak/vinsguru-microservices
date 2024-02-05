package com.vinsguru.redisspring.chat.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import com.vinsguru.redisspring.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ChatRoomSocketConfig {

	private final ChatRoomService chatRoomService;

	@Bean
	public HandlerMapping handlerMapping() {
		Map<String, WebSocketHandler> map = Map.of("/chat", chatRoomService);
		return new SimpleUrlHandlerMapping(map, -1);
	}

}
