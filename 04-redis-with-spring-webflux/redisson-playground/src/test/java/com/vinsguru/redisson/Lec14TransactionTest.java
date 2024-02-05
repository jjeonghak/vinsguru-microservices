package com.vinsguru.redisson;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RTransactionReactive;
import org.redisson.api.TransactionOptions;
import org.redisson.client.codec.LongCodec;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec14TransactionTest extends BaseTest {

	private RBucketReactive<Long> user1Balance;
	private RBucketReactive<Long> user2Balance;

	@BeforeAll
	private void dataSetup() {
		this.user1Balance = this.client.getBucket("user:1:balance", LongCodec.INSTANCE);
		this.user2Balance = this.client.getBucket("user:2:balance", LongCodec.INSTANCE);

		Mono<Void> setup = user1Balance.set(100L)
				.then(user2Balance.set(0L))
				.then();

		StepVerifier.create(setup)
				.verifyComplete();
	}

	@AfterAll
	public void dataStatus() {
		Mono<Void> status = Flux.zip(this.user1Balance.get(), this.user2Balance.get())
				.doOnNext(System.out::println)
				.then();

		StepVerifier.create(status)
				.verifyComplete();
	}

	@Test
	public void nonTransactionTest() {

		this.transfer(this.user1Balance, this.user2Balance, 50)
				.thenReturn(0)
				.map(i -> 5 / i)
				.doOnError(System.out::println)
				.subscribe();

		sleep(1000);

	}

	@Test
	public void transactionTest() {

		RTransactionReactive transaction = this.client.createTransaction(TransactionOptions.defaults());
		RBucketReactive<Long> tran1 = transaction.getBucket("user:1:balance", LongCodec.INSTANCE);
		RBucketReactive<Long> tran2 = transaction.getBucket("user:2:balance", LongCodec.INSTANCE);

		this.transfer(tran1, tran2, 50)
				.thenReturn(0)
				.map(i -> 5 / i)
				.then(transaction.commit())
				.doOnError(System.out::println)
				.onErrorResume(ex -> transaction.rollback())
				.subscribe();

		sleep(1000);

	}

	private Mono<Void> transfer(RBucketReactive<Long> from, RBucketReactive<Long> to, int amount) {
		return Flux.zip(from.get(), to.get())
				.filter(t -> t.getT1() >= amount)
				.flatMap(t -> from.set(t.getT1() - amount).thenReturn(t))
				.flatMap(t -> to.set(t.getT2() + amount))
				.then();
	}



}
