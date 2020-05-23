package com.avasthi.varahamihir.gateway.gateway;

import com.avasthi.varahamihir.gateway.gateway.domain.ProductResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/oauth2**").filters(f ->
						f.hystrix(c -> c.setName("oauth2").setFallbackUri("forward:/fallback"))).uri("lb://identity-server"))
				.route(p -> p.path("/guardian/**").filters(f ->
						f.hystrix(c -> c.setName("guardian").setFallbackUri("forward:/fallback"))).uri("lb://guardian"))
				.route(p -> p.path("/lessons/**").filters(f ->
						f.hystrix(c -> c.setName("lessons").setFallbackUri("forward:/fallback"))).uri("lb://lessons"))
				.build();
	}

	@GetMapping("/fallback")
	public ResponseEntity<List<ProductResponse>> fallback() {
		System.out.println("fallback enabled");
		HttpHeaders headers = new HttpHeaders();
		headers.add("fallback", "true");
		return ResponseEntity.ok().headers(headers).body(Collections.emptyList());
	}

}
