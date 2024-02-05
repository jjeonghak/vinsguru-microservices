package com.vinsguru.springrsocket.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class UserRepository {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private Map<String, UserDetails> database;

	@PostConstruct
	private void init() {
		this.database = Map.of(
				"user", User.withUsername("user").password(passwordEncoder.encode("password")).roles("USER").build(),
				"admin", User.withUsername("admin").password(passwordEncoder.encode("password")).roles("ADMIN").build(),
				"client", User.withUsername("client").password(passwordEncoder.encode("password")).roles("TRUSTED_CLIENT").build()
		);
	}

	public UserDetails findByUsername(String username) {
		return this.database.get(username);
	}

}
