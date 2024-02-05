package com.vinsguru.springrsocket.security;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.vinsguru.springrsocket.dto.ComputationRequestDto;
import com.vinsguru.springrsocket.dto.ComputationResponseDto;
import com.vinsguru.springrsocket.service.MathService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@MessageMapping("math.service.secured")
public class SecuredMathController {

	private final MathService service;

	@PreAuthorize("hasRole('USER')")
	@MessageMapping("square")
	public Mono<ComputationResponseDto> findSquare(
			Mono<ComputationRequestDto> requestDtoMono,
			@AuthenticationPrincipal Mono<UserDetails> userDetailsMono) {
		userDetailsMono.doOnNext(System.out::println).subscribe();
		return service.findSquare(requestDtoMono);
	}

	@MessageMapping("table")
	public Flux<ComputationResponseDto> tableStream(Mono<ComputationRequestDto> requestDtoMono) {
		return requestDtoMono.flatMapMany(service::tableStream);
	}

}
