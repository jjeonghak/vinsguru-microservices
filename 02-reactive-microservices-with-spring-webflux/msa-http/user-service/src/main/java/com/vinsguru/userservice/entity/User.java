package com.vinsguru.userservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.vinsguru.userservice.dto.UserDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@Table(value = "users")
public class User {

	@Id
	private Long id;
	private String name;
	private Integer balance;

	public static User persist(UserDto dto) {
		return User.builder()
				.name(dto.getName())
				.balance(dto.getBalance())
				.build();
	}

	public User update(UserDto dto) {
		this.name = dto.getName();
		this.balance = dto.getBalance();
		return this;
	}

}
