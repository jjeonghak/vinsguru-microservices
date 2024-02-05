package com.vinsguru.redisson;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;

import reactor.core.publisher.Mono;

public class Lec05EventListenerTest extends BaseTest {

	@Test
	public void expiredEventTest() {

		//redis must do command 'config set notify-keyspace-events AKE'
		RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("sam", Duration.ofSeconds(10));
		Mono<Void> get = bucket.get()
				.doOnNext(System.out::println)
				.then();

		//need ExpiredObjectListener
		Mono<Void> expired = bucket.addListener(new ExpiredObjectListener() {
						@Override
						public void onExpired(String key) {
							System.out.println("Lec05EventListenerTest: expired key " + key);
						}
				})
				.then();

		set.concatWith(get).concatWith(expired).subscribe();

		//extend
		sleep(11000);

	}

	@Test
	public void deletedEventTest() {

		//redis must do command 'config set notify-keyspace-events AKE'
		RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
		Mono<Void> set = bucket.set("sam", Duration.ofSeconds(10));
		Mono<Void> get = bucket.get()
				.doOnNext(System.out::println)
				.then();

		//need DeletedObjectListener
		Mono<Void> deleted = bucket.addListener(new DeletedObjectListener() {
						@Override
						public void onDeleted(String key) {
							System.out.println("Lec05EventListenerTest: deleted key " + key);
						}
				})
				.then();

		set.concatWith(get).concatWith(deleted).subscribe();

		//extend
		sleep(15000);

	}

}
