package com.avasthi.varahamihir.gateway;

import java.util.Collections;
import java.util.List;

import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Probe;
import io.dekorate.kubernetes.annotation.ServiceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir.gateway"})
@RestController
@EnableDiscoveryClient
@KubernetesApplication(livenessProbe = @Probe(httpActionPath = "/manage/health"),
				readinessProbe = @Probe(httpActionPath = "/manage/health"),
				serviceType = ServiceType.NodePort,
				imagePullPolicy = ImagePullPolicy.Always)
public class GatewayApplication {

	@Autowired
	private DiscoveryClient discoveryClient;
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {

		for (String s : discoveryClient.getServices()) {
			log.error(String.format("Service name %s",s));
		}
		return builder.routes()
						.route(p -> p.path("/*/oauth/**").filters(f ->
										f.hystrix(c -> c.setName("oauth2").setFallbackUri("forward:/fallback"))).uri("lb://identity-server"))
						.route(p -> p.path("/*/registration/**").filters(f ->
										f.hystrix(c -> c.setName("oauth2").setFallbackUri("forward:/fallback"))).uri("lb://identity-server"))
						.route(p -> p.path("/*/guardian/**").filters(f ->
										f.hystrix(c -> c.setName("guardian").setFallbackUri("forward:/fallback"))).uri("lb://guardian"))
						.route(p -> p.path("/*/lessons/**").filters(f ->
										f.hystrix(c -> c.setName("lessons").setFallbackUri("forward:/fallback"))).uri("lb://lessons"))
						.build();
	}

	@RequestMapping("/fallback")
	public ResponseEntity<List<String>> fallback() {
		System.out.println("fallback enabled");
		HttpHeaders headers = new HttpHeaders();
		headers.add("fallback", "true");
		return ResponseEntity.ok().headers(headers).body(Collections.emptyList());
	}

}
