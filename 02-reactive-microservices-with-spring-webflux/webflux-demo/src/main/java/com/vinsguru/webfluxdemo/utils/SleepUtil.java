package com.vinsguru.webfluxdemo.utils;

import org.springframework.stereotype.Component;

@Component
public class SleepUtil {

	public static void sleepSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
