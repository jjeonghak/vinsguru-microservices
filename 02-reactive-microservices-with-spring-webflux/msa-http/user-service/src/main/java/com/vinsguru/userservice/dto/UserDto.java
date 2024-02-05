package com.vinsguru.userservice.dto;

import com.vinsguru.userservice.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	private String name;
	private Integer balance;

	public static UserDto from(User user) {
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.balance(user.getBalance())
				.build();
	}

}
