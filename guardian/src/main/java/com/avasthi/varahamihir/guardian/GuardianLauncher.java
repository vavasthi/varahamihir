package com.avasthi.varahamihir.guardian;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Probe;
import io.dekorate.kubernetes.annotation.ServiceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@EnableWebFlux
@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir"})
@Configuration
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.avasthi.varahamihir"})
@ComponentScan(basePackages = {"com.avasthi.varahamihir"})
@EnableJpaRepositories("com.avasthi.varahamihir")
@EnableFeignClients(basePackages = {"com.avasthi.varahamihir.client.proxies"})
//@EnableSwagger2
@KubernetesApplication(livenessProbe = @Probe(httpActionPath = "/actuator/health"),
        readinessProbe = @Probe(httpActionPath = "/actuator/health"),
        serviceType = ServiceType.NodePort,
        imagePullPolicy = ImagePullPolicy.Always)
@PropertySource("classpath:jwt.properties")
public class GuardianLauncher {

  public static void main(String[] args) {

    SpringApplication.run(GuardianLauncher.class, args);
  }
  @PostConstruct
  public void initialize() {

  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public Module jodaModule() {
    return new JodaModule();
  }

  public RouteLocator routes(RouteLocatorBuilder builder) {

    return builder.routes()
            .route(p -> p.path("/*/identity-server/**").filters(f ->
                    f.hystrix(c -> c.setName("identity-server").setFallbackUri("forward:/fallback"))).uri("lb://identity-server:8081"))
            .route(p -> p.path("/*/student/**").filters(f ->
                    f.hystrix(c -> c.setName("student").setFallbackUri("forward:/fallback"))).uri("lb://student:8081"))
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
