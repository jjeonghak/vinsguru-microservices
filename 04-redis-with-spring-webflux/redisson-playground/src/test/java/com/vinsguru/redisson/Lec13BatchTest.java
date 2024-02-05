package com.vinsguru.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.BatchOptions;
import org.redisson.api.RBatchReactive;
import org.redisson.api.RListReactive;
import org.redisson.api.RSetReactive;
import org.redisson.client.codec.LongCodec;

import com.vinsguru.redisson.BaseTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec13BatchTest extends BaseTest {

	//15.314 sec
	@Test
	public void batchTest() {

		//batch task init
		RBatchReactive batch = this.client.createBatch(BatchOptions.defaults());
		RListReactive<Long> list = batch.getList("numbers-list", LongCodec.INSTANCE);
		RSetReactive<Long> set = batch.getSet("numbers-set", LongCodec.INSTANCE);
		for (long i = 0; i < 500000; i++) {
			list.add(i);
			set.add(i);
		}

		//batch start
		StepVerifier.create(batch.execute().then())
				.verifyComplete();

	}

	//57.696 sec
	@Test
	public void regularTest() {

		RListReactive<Long> list = this.client.getList("numbers-list", LongCodec.INSTANCE);
		RSetReactive<Long> set = this.client.getSet("numbers-set", LongCodec.INSTANCE);

		Mono<Void> task = Flux.range(1, 500000)
				.map(Long::valueOf)
				.flatMap(i -> list.add(i).then(set.add(i)))
				.then();

		StepVerifier.create(task)
				.verifyComplete();

	}

}
