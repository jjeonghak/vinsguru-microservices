package com.vinsguru.graphqlplayground.sec14.util;

import org.springframework.beans.BeanUtils;

import com.vinsguru.graphqlplayground.sec14.dto.CustomerDto;
import com.vinsguru.graphqlplayground.sec14.entity.Customer;

public class EntityDtoUtil {

	public static Customer toEntity(CustomerDto dto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(dto, customer);
		return customer;
	}

	public static Customer toEntity(Long id, CustomerDto dto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(dto, customer);
		customer.setId(id);
		return customer;
	}

	public static CustomerDto toDto(Customer customer) {
		CustomerDto dto = new CustomerDto();
		BeanUtils.copyProperties(customer, dto);
		return dto;
	}

}
