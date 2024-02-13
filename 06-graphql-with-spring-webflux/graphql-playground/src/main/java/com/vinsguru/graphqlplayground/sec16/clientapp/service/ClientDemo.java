package com.vinsguru.graphqlplayground.sec16.clientapp.service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec16.clientapp.client.CustomerClient;
import com.vinsguru.graphqlplayground.sec16.clientapp.client.SubscriptionClient;
import com.vinsguru.graphqlplayground.sec16.dto.CustomerDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientDemo implements CommandLineRunner {

	private final CustomerClient client;
	private final SubscriptionClient subscriptionClient;

	@Override
	public void run(String... args) throws Exception {
		this.subscriptionClient
				.customerEvents()
				.doOnNext(e -> System.out.println(" ** " + e.toString() + " ** "))
				.subscribe();

		// rawQueryDemo()
				// .then(getCustomerById())
				// .then(getCustomerByIdWithError())
				// .then(getCustomerByIdWithUnion())
				// .subscribe();

		allCustomersDemo()
				.then(customerByIdDemo())
				.then(createCustomerDemo())
				.then(updateCustomerDemo())
				.then(deleteCustomerDemo())
				.subscribe();
	}

	private Mono<Void> rawQueryDemo() {
		String query = """
				{
					alias: customers {
						id
						name
						age
						city
					}
				}
				""";

		Mono<List<CustomerDto>> customers = this.client.rawQuery(query)
				.map(cr -> cr.field("alias").toEntityList(CustomerDto.class));

		return executor("rawQueryDemo", customers);
	}

	private Mono<Void> getCustomerById() {
		return executor("getCustomerById", this.client.getCustomerById(1L));
	}

	private Mono<Void> getCustomerByIdWithError() {
		return executor("getCustomerByIdWithError", this.client.getCustomerByIdWithError(10L));
	}

	private Mono<Void> getCustomerByIdWithUnion() {
		long id = ThreadLocalRandom.current().nextLong(1, 10);
		return executor("getCustomerByIdWithUnion", this.client.getCustomerByIdWithUnion(id));
	}

	private Mono<Void> allCustomersDemo() {
		return executor("allCustomersDemo", this.client.allCustomers());
	}

	private Mono<Void> customerByIdDemo() {
		return executor("customerByIdDemo", this.client.customerById(1L));
	}

	private Mono<Void> createCustomerDemo() {
		return executor("createCustomerDemo", this.client.createCustomer(CustomerDto.create(null, "jeonghak", 28, "seoul")));
	}

	private Mono<Void> updateCustomerDemo() {
		return executor("updateCustomerDemo", this.client.updateCustomer(5L, CustomerDto.create(null, "update jeonghak", 30, "seoul")));
	}

	private Mono<Void> deleteCustomerDemo() {
		return executor("deleteCustomerDemo", this.client.deleteCustomer(5L));
	}

	private Mono<Void> executor(String msg, Mono<?> publisher) {
		return Mono.delay(Duration.ofSeconds(1))
				.doFirst(() -> System.out.println("ClientDemo: call " + msg))
				.then(publisher)
				.doOnNext(System.out::println)
				.then();
	}

}
