package com.vinsguru.productservice.service;

import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.vinsguru.productservice.dto.ProductDto;
import com.vinsguru.productservice.entity.Product;
import com.vinsguru.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final Sinks.Many<ProductDto> sink;
	private final ProductRepository repository;

	public Flux<ProductDto> getAll() {
		return repository.findAll()
				.map(ProductDto::from);
	}

	public Mono<ProductDto> getProductById(String id) {
		return repository.findById(id)
				.map(ProductDto::from);
	}

	public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
		return productDtoMono
				.map(Product::persist)
				.flatMap(repository::insert)
				.map(ProductDto::from)
				.doOnNext(sink::tryEmitNext);
	}

	public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
		return productDtoMono
				.flatMap(dto -> repository.findById(id)
						.map(p -> p.update(dto))
						.doOnNext(p -> p.setId(id))
				)
				.flatMap(repository::save)
				.map(ProductDto::from);
	}

	public Mono<Void> deleteProduct(String id) {
		return repository.deleteById(id);
	}

	public Flux<ProductDto> getProductByPriceRange(int min, int max) {
		return repository.findByPriceBetween(Range.closed(min, max))
				.map(ProductDto::from);
	}

}
