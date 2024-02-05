package com.vinsguru.userservice.service;

import org.springframework.stereotype.Service;

import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public Flux<UserDto> getAllUsers() {
		return userRepository.findAll()
				.map(EntityDtoUtil::toDto);
	}

	public Mono<UserDto> getUserById(String userId) {
		return userRepository.findById(userId)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<UserDto> createUser(Mono<UserDto> mono) {
		return mono
				.map(EntityDtoUtil::toEntity)
				.flatMap(userRepository::save)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<UserDto> updateUser(String userId, Mono<UserDto> mono) {
		return userRepository.findById(userId)
				.flatMap(u -> mono.map(EntityDtoUtil::toEntity)
						.doOnNext(e -> e.setId(userId))
				)
				.flatMap(userRepository::save)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<Void> deleteUser(String userId) {
		return userRepository.deleteById(userId);
	}

}
