package com.vinsguru.redisson;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;

import com.vinsguru.redisson.dto.Student;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02KeyValueObjectTest extends BaseTest {

	@Test
	public void keyValueObjectTest() {

		//must set up json jackson codec or implement serializable
		Student student = new Student("marshal", 10, "01012345678", List.of(1, 2));
		// RBucketReactive<Student> bucket = this.client.getBucket("student:1", JsonJacksonCodec.INSTANCE);
		RBucketReactive<Student> bucket = this.client.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));

		Mono<Void> set = bucket.set(student);
		Mono<Void> get = bucket.get()
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(set.concatWith(get))
				.verifyComplete();

	}

}
