package com.vinsguru.graphqlplayground;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.TestPropertySource;

import com.vinsguru.graphqlplayground.sec13.dto.DeleteResponseDto;
import com.vinsguru.graphqlplayground.sec13.dto.Status;
import com.vinsguru.graphqlplayground.sec16.dto.CustomerDto;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
@TestPropertySource(properties = "sec=sec14")
public class GraphqlCrudTest {

	@Autowired
	private HttpGraphQlTester client;

	@Test
	public void allCustomersTest() {

		var doc = """
					query {
						customers {
							name
							age
						}
					}
				""";

		this.client.document(doc)
				.execute()
				.path("customers").entityList(Object.class).hasSizeGreaterThan(2)
				.path("customers.[0].name").entity(String.class).isEqualTo("sam");

	}

	@Test
	public void customerByIdTest() {

		this.client.documentName("crud-operations")
				.variable("id", 1)
				.operationName("GetCustomerById")
				.execute()
				.path("response.id").entity(Long.class).isEqualTo(1L)
				.path("response.name").entity(String.class).isEqualTo("sam")
				.path("response.age").entity(Integer.class).isEqualTo(10)
				.path("response.city").entity(String.class).isEqualTo("atlanta");

	}

	@Test
	public void createCustomerTest() {

		this.client.documentName("crud-operations")
				.variable("customer", CustomerDto.create(null, "jeonghak", 28, "seoul"))
				.operationName("CreateCustomer")
				.execute()
				.path("response.id").entity(Long.class).isEqualTo(5L)
				.path("response.name").entity(String.class).isEqualTo("jeonghak")
				.path("response.age").entity(Integer.class).isEqualTo(28)
				.path("response.city").entity(String.class).isEqualTo("seoul");

	}

	@Test
	public void updateCustomerTest() {

		this.client.documentName("crud-operations")
				.variable("id", 1)
				.variable("customer", CustomerDto.create(null, "jeonghak", 28, "seoul"))
				.operationName("UpdateCustomer")
				.execute()
				.path("response.id").entity(Long.class).isEqualTo(1L)
				.path("response.name").entity(String.class).isEqualTo("jeonghak")
				.path("response.age").entity(Integer.class).isEqualTo(28)
				.path("response.city").entity(String.class).isEqualTo("seoul")
				.path("response").entity(Object.class).satisfies(System.out::println);

	}

	@Test
	public void deleteCustomerTest() {

		this.client.documentName("crud-operations")
				.variable("id", 1)
				.operationName("DeleteCustomer")
				.execute()
				// .path("response.id").entity(Long.class).isEqualTo(1L)
				// .path("response.status").entity(String.class).isEqualTo("SUCCESS");
				.path("response").entity(DeleteResponseDto.class).satisfies(r -> {
						Assertions.assertThat(r.getId()).isEqualTo(1L);
						Assertions.assertThat(r.getStatus()).isEqualTo(Status.SUCCESS);
				});

	}

}
