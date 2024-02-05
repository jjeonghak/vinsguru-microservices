package com.vinsguru.redisson.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
// public class Student implements Serializable {
public class Student {

	private String name;
	private int age;
	private String phone;
	private List<Integer> integers;

}
