package com.vinsguru.graphqlplayground;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

import com.vinsguru.graphqlplayground.sec16.dto.CustomerDto;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "sec=sec15")
public class GraphqlErrorTest {

	@Autowired
	private HttpGraphQlTester client;

	@Test
	public void createCustomerTest() {

		this.client
				.mutate().header("caller-id", "jeonghak").build()
				.documentName("crud-operations")
				.variable("customer", CustomerDto.create(null, "jeonghak", 15, "seoul"))
				.operationName("CreateCustomer")
				.execute()
				.errors().satisfy(list -> {
						Assertions.assertThat(list).hasSize(1);
						Assertions.assertThat(list.get(0).getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
				})
				.path("response").valueIsNull()
				.path("response.id").pathDoesNotExist();

	}

}
