package com.vinsguru.graphqlplayground.sec16.clientapp.client;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.ClientResponseField;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec16.dto.CustomerDto;
import com.vinsguru.graphqlplayground.sec16.dto.CustomerNotFound;
import com.vinsguru.graphqlplayground.sec16.dto.CustomerResponse;
import com.vinsguru.graphqlplayground.sec16.dto.DeleteResponseDto;
import com.vinsguru.graphqlplayground.sec16.dto.GenericResponse;
import com.vinsguru.graphqlplayground.sec16.dto.MultiCustomerAssignmentDto;

import reactor.core.publisher.Mono;

@Service
public class CustomerClient {

	private final HttpGraphQlClient client;

	public CustomerClient(@Value("${customer.service.url.sec16}") String url) {
		this.client = HttpGraphQlClient.builder()
				.webClient(b -> b.baseUrl(url))
				.build();
	}

	public Mono<ClientGraphQlResponse> rawQuery(String query) {
		return this.client.document(query)
				.execute();
	}

	public Mono<MultiCustomerAssignmentDto> getCustomerById(Long id) {
		return this.client.documentName("customer-by-id")
				.variable("id", id)
				.execute()
				.map(cr -> cr.toEntity(MultiCustomerAssignmentDto.class));
	}

	public Mono<GenericResponse<CustomerDto>> getCustomerByIdWithError(Long id) {
		return this.client.documentName("customer-by-id-with-error")
				.variable("id", id)
				.execute()
				.map(cr -> {
						ClientResponseField field = cr.field("customerById");
						return new GenericResponse<>(field.getValue(), field.getErrors());
				});
	}

	public Mono<CustomerResponse> getCustomerByIdWithUnion(Long id) {
		return this.client.documentName("customer-by-id-with-union")
				.variable("id", id)
				.execute()
				.map(cr -> {
						ClientResponseField field = cr.field("customerByIdWithUnion");
						String type =  Objects.requireNonNull(cr.field("customerByIdWithUnion.type").getValue()).toString();
						return type.equals("Customer") ?
								field.toEntity(CustomerDto.class) :
								field.toEntity(CustomerNotFound.class);
				});
	}

	public Mono<List<CustomerDto>> allCustomers() {
		return this.crud("GetAll", Collections.emptyMap(), new ParameterizedTypeReference<List<CustomerDto>>() {});
	}

	public Mono<CustomerDto> customerById(Long id) {
		return this.crud("GetCustomerById", Map.of("id", id), new ParameterizedTypeReference<CustomerDto>() {});
	}

	public Mono<CustomerDto> createCustomer(CustomerDto dto) {
		return this.crud("CreateCustomer", Map.of("customer", dto), new ParameterizedTypeReference<CustomerDto>() {});
	}

	public Mono<CustomerDto> updateCustomer(Long id, CustomerDto dto) {
		return this.crud("UpdateCustomer", Map.of("id", id, "customer", dto), new ParameterizedTypeReference<CustomerDto>() {});
	}

	public Mono<DeleteResponseDto> deleteCustomer(Long id) {
		return this.crud("DeleteCustomer", Map.of("id", id), new ParameterizedTypeReference<DeleteResponseDto>() {});
	}

	private <T> Mono<T> crud(String operationName, Map<String, Object> variables, ParameterizedTypeReference<T> type) {
		return this.client
				// if you want to set header, use any line
				// .mutate().header("header-key", "header-value").build()
				// .mutate().webClient(b -> b.defaultHeaders(h -> h.setBearerAuth("yourAccessToken"))).build()
				.documentName("crud-operations")
				.operationName(operationName)
				.variables(variables)
				.retrieve("response")
				.toEntity(type);
	}

}
