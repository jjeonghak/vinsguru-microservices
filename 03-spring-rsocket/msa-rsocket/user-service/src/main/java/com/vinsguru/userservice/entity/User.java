package com.vinsguru.userservice.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class User {

	@Id
	private String id;
	private String name;
	private int balance;

	public User(String name, int balance) {
		this.name = name;
		this.balance = balance;
	}

}
