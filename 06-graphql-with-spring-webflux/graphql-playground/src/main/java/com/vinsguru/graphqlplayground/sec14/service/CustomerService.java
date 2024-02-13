package com.vinsguru.graphqlplayground.sec14.service;

import org.springframework.stereotype.Service;

import com.vinsguru.graphqlplayground.sec14.dto.Action;
import com.vinsguru.graphqlplayground.sec14.dto.CustomerDto;
import com.vinsguru.graphqlplayground.sec14.dto.CustomerEvent;
import com.vinsguru.graphqlplayground.sec14.dto.DeleteResponseDto;
import com.vinsguru.graphqlplayground.sec14.dto.Status;
import com.vinsguru.graphqlplayground.sec14.repository.CustomerRepository;
import com.vinsguru.graphqlplayground.sec14.util.EntityDtoUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository repository;

	private final CustomerEventService eventService;

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
				.map(EntityDtoUtil::toDto)
				.doOnNext(c -> this.eventService.emitEvent(CustomerEvent.create(c.getId(), Action.CREATED)));
	}

	public Mono<CustomerDto> updateCustomer(long id, CustomerDto dto) {
		return this.repository.findById(id)
				.map(c -> EntityDtoUtil.toEntity(id, dto))
				.flatMap(this.repository::save)
				.map(EntityDtoUtil::toDto)
				.doOnNext(c -> this.eventService.emitEvent(CustomerEvent.create(c.getId(), Action.UPDATED)));
	}

	public Mono<DeleteResponseDto> deleteCustomer(long id) {
		return this.repository.findById(id)
				.doOnNext(c -> this.eventService.emitEvent(CustomerEvent.create(c.getId(), Action.DELETED)))
				.then(this.repository.deleteById(id))
				.thenReturn(DeleteResponseDto.create(id, Status.SUCCESS))
				.onErrorReturn(DeleteResponseDto.create(id, Status.FAILURE));
	}

}
