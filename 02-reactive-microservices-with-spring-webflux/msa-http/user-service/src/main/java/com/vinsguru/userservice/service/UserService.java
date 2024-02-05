package com.vinsguru.userservice.service;

import org.springframework.stereotype.Service;

import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public Flux<UserDto> all() {
		return userRepository.findAll()
				.map(UserDto::from);
	}

	public Mono<UserDto> getUserById(Long userId) {
		return userRepository.findById(userId)
				.map(UserDto::from);
	}

	public Mono<UserDto> createUser(Mono<UserDto> dtoMono) {
		return dtoMono
				.map(User::persist)
				.flatMap(userRepository::save)
				.map(UserDto::from);
	}

	public Mono<UserDto> updateUser(Long id, Mono<UserDto> userDtoMono) {
		return userDtoMono
				.flatMap(dto -> userRepository.findById(id)
						.map(u -> u.update(dto))
						.flatMap(userRepository::save)
				)
				.map(UserDto::from);
	}

	public Mono<Void> deleteUser(Long id) {
		return userRepository.deleteById(id);
	}

}
