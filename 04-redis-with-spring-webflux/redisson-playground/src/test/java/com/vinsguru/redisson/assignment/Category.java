package com.vinsguru.redisson.assignment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

	PRIME(100), STD(200), GUEST(300);

	private final int value;

}
