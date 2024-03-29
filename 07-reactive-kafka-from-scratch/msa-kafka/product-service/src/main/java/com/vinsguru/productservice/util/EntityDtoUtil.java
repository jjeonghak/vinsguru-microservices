package com.vinsguru.productservice.util;

import org.springframework.beans.BeanUtils;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.entity.Product;

public class EntityDtoUtil {

	public static ProductDto toDto(Product product) {
		var dto = new ProductDto();
		BeanUtils.copyProperties(product, dto);
		return dto;
	}

}
