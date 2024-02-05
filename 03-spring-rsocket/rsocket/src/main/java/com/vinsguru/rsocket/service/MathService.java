package com.vinsguru.rsocket.service;

import java.time.Duration;

import org.reactivestreams.Publisher;

import com.vinsguru.rsocket.dto.ChartResponseDto;
import com.vinsguru.rsocket.dto.RequestDto;
import com.vinsguru.rsocket.dto.ResponseDto;
import com.vinsguru.rsocket.util.ObjectUtil;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MathService implements RSocket {

	@Override
	public Mono<Void> fireAndForget(Payload payload) {
		System.out.println("MathService: receiving " + ObjectUtil.toObject(payload, RequestDto.class));
		return Mono.empty();
	}

	@Override
	public Mono<Payload> requestResponse(Payload payload) {
		return Mono.fromSupplier(() -> {
				RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
				int input = requestDto.getInput();
				ResponseDto responseDto = new ResponseDto(input, input * input);
				return ObjectUtil.toPayload(responseDto);
		});
	}

	@Override
	public Flux<Payload> requestStream(Payload payload) {
		RequestDto requestDto = ObjectUtil.toObject(payload, RequestDto.class);
		return Flux.range(1, 10)
				.map(i -> i * requestDto.getInput())
				.map(output -> new ResponseDto(requestDto.getInput(), output))
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(System.out::println)
				.doOnSubscribe(s -> System.out.println("RSocket: subscribe"))
				.doOnCancel(() -> System.out.println("RSocket: cancel"))
				.doOnComplete(() -> System.out.println("RSocket: complete"))
				.map(ObjectUtil::toPayload);
	}

	@Override
	public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
		return Flux.from(payloads)
				.map(p -> ObjectUtil.toObject(p, RequestDto.class))
				.map(RequestDto::getInput)
				.map(i -> new ChartResponseDto(i, (i * i) + 1))
				.doOnNext(System.out::println)
				.doOnSubscribe(s -> System.out.println("RSocket: subscribe"))
				.doOnCancel(() -> System.out.println("RSocket: cancel"))
				.doOnComplete(() -> System.out.println("RSocket: complete"))
				.map(ObjectUtil::toPayload);
	}

}
