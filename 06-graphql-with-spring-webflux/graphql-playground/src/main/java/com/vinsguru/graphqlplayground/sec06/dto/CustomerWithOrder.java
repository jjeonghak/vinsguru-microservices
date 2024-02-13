package com.vinsguru.graphqlplayground.sec06.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWithOrder {

	private Long id;
	private String name;
	private Integer age;
	private String city;
	private List<CustomerOrder> orders;

	public static CustomerWithOrder create(Customer customer, List<CustomerOrder> orders) {
		return CustomerWithOrder.builder()
				.id(customer.getId())
				.name(customer.getName())
				.age(customer.getAge())
				.city(customer.getCity())
				.orders(orders)
				.build();
	}

}
