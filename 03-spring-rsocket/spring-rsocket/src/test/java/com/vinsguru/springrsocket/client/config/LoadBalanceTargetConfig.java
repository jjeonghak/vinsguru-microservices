package com.vinsguru.springrsocket.client.config;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vinsguru.springrsocket.client.serviceregistry.RSocketServerInstance;
import com.vinsguru.springrsocket.client.serviceregistry.ServiceRegistryClient;

import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class LoadBalanceTargetConfig {

	@Autowired
	private ServiceRegistryClient registryClient;

	@Bean
	public Flux<List<LoadbalanceTarget>> targetFlux() {
		return Flux.from(targets());

		// return Flux.interval(Duration.ofSeconds(5))
		// 		.flatMap(i -> targets())
		// 		.doOnNext(l -> l.remove(ThreadLocalRandom.current().nextInt(0, 3)));
	}

	private Mono<List<LoadbalanceTarget>> targets() {
		return Mono.fromSupplier(() -> this.registryClient.getInstances().stream()
				.map(server -> LoadbalanceTarget.from(key(server), transport(server)))
				.collect(Collectors.toList())
		);
	}

	private String key(RSocketServerInstance instance) {
		return instance.getHost() + instance.getPort();
	}

	private ClientTransport transport(RSocketServerInstance instance) {
		return TcpClientTransport.create(instance.getHost(), instance.getPort());
	}

}
