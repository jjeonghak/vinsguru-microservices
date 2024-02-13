package com.vinsguru.graphqlplayground.sec13.service;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec13.dto.CustomerDto;
import com.vinsguru.graphqlplayground.sec13.dto.DeleteResponseDto;
import com.vinsguru.graphqlplayground.sec13.dto.Status;
import com.vinsguru.graphqlplayground.sec13.repository.CustomerRepository;
import com.vinsguru.graphqlplayground.sec13.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository repository;

	public Flux<CustomerDto> allCustomers() {
		return this.repository.findAll()
				.map(EntityDtoUtil::toDto);
	}

	public Mono<CustomerDto> customerById(long id) {
		return this.repository.findById(id)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<CustomerDto> createCustomer(CustomerDto dto) {
		return Mono.just(dto)
				.map(EntityDtoUtil::toEntity)
				.flatMap(this.repository::save)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<CustomerDto> updateCustomer(long id, CustomerDto dto) {
		return this.repository.findById(id)
				.map(c -> EntityDtoUtil.toEntity(id, dto))
				.flatMap(this.repository::save)
				.map(EntityDtoUtil::toDto);
	}

	public Mono<DeleteResponseDto> deleteCustomer(long id) {
		return this.repository.deleteById(id)
				.thenReturn(DeleteResponseDto.create(id, Status.SUCCESS))
				.onErrorReturn(DeleteResponseDto.create(id, Status.FAILURE));
	}

}
