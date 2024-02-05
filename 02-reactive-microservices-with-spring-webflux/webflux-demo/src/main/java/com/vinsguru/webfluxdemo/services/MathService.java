package com.vinsguru.webfluxdemo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.utils.SleepUtil;

@Service
public class MathService {

	public Response findSquare(int input) {
		return new Response(input * input, LocalDate.now());
	}

	public List<Response> multiplicationTable(int input) {
		return IntStream.range(1, 10)
				.peek(i -> SleepUtil.sleepSeconds(1))
				.peek(i -> System.out.println("MathService: processing " + i))
				.mapToObj(i -> new Response(i * input, LocalDate.now()))
				.collect(Collectors.toList());
	}

}
