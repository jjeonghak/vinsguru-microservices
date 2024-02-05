package com.vinsguru.redisspring.chat.service;

import java.net.URI;

import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements WebSocketHandler {

	private final RedissonReactiveClient client;

	@Override
	public Mono<Void> handle(WebSocketSession webSocketSession) {

		String room = getChatRoomName(webSocketSession);
		RTopicReactive topic = this.client.getTopic(room, StringCodec.INSTANCE);
		RListReactive<String> list = this.client.getList("history:" + room, StringCodec.INSTANCE);

		//subscribe
		webSocketSession.receive()
				.map(WebSocketMessage::getPayloadAsText)
				// .flatMap(topic::publish)
				.flatMap(msg -> list.add(msg).then(topic.publish(msg)))
				.doOnError(System.out::println)
				.doFinally(s -> System.out.println("ChatRoomService: subscriber finally " + s))
				.subscribe();

		//publisher
		Flux<WebSocketMessage> webSocketMessageFlux = topic.getMessages(String.class)
				.startWith(list.iterator())
				.map(webSocketSession::textMessage)
				.doOnError(System.out::println)
				.doFinally(s -> System.out.println("ChatRoomService: publisher finally " + s));

		return webSocketSession.send(webSocketMessageFlux);
	}

	private String getChatRoomName(WebSocketSession webSocketSession) {
		URI uri = webSocketSession.getHandshakeInfo().getUri();
		return UriComponentsBuilder.fromUri(uri)
				.build()
				.getQueryParams()
				.toSingleValueMap()
				.getOrDefault("room", "default");
	}

}
