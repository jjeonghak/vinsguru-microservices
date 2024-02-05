package com.vinsguru.redisspring.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {

	@Cacheable("math:fib")
	public int getFib(int index) {
		System.out.println("FibService: calculate fib for " + index);
		return fib(index);
	}

	//have a strategy for cache evict
	@Cacheable(value = "math:fib", key = "#index")
	public int getFib(int index, String name) {
		System.out.println("FibService: calculate fib for " + index + " and name " + name);
		return fib(index);
	}

	//must use when method is in PUT/POST/PATCH/DELETE
	@CacheEvict(value = "math:fib", key = "#index")
	public void clearCache(int index) {
		System.out.println("FibService: clear hash key " + index);
	}

	@Scheduled(fixedRate = 10000)
	@CacheEvict(value = "math:fib", allEntries = true)
	public void clearCache() {
		System.out.println("FibService: clear all keys");
	}


	//intentional 2^N
	private int fib(int index) {
		if (index < 2) {
			return index;
		}
		return fib(index - 1) + fib(index - 2);
	}

}
