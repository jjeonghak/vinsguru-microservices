package com.vinsguru.userservice.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

	private final UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User("sam", 10000);
		User user2 = new User("mike", 10000);
		User user3 = new User("jake", 10000);

		userRepository.deleteAll().block();

		Flux.just(user1, user2, user3)
				.flatMap(userRepository::save)
				.doOnNext(System.out::println)
				.subscribe();
	}

}
