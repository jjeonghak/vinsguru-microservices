package com.vinsguru.analyticsservice.entity;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewCount implements Persistable<Long> {

	@Id
	private Long id;
	private Long count;

	@Transient
	private boolean isNew;

	@Override
	public boolean isNew() {
		return this.isNew || Objects.isNull(this.id);
	}

}
