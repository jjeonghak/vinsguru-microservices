package com.vinsguru.webfluxdemo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxdemo.dtos.Response;
import com.vinsguru.webfluxdemo.services.MathService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/math")
public class MathController {

	private final MathService service;

	@GetMapping("/square/{input}")
	public Response findSquare(@PathVariable(value = "input") int input) {
		return service.findSquare(input);
	}

	@GetMapping("/table/{input}")
	public List<Response> multiplicationTable(@PathVariable(value = "input") int input) {
		return service.multiplicationTable(input);
	}

}
