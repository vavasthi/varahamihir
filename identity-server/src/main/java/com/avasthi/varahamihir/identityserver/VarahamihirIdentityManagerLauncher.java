
package com.avasthi.varahamihir.identityserver;

import com.avasthi.varahamihir.common.constants.MyConstants;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.services.ClientService;
import com.avasthi.varahamihir.identityserver.services.TenantService;
import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Probe;
import io.dekorate.kubernetes.annotation.ServiceType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by vinay on 1/8/16.
 */
@EnableWebFlux
@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir"})
@Configuration
//@EnableDiscoveryClient
@EntityScan(basePackages = {"com.avasthi.varahamihir"})
@EnableJpaRepositories("com.avasthi.varahamihir")
//@EnableSwagger2
@KubernetesApplication(livenessProbe = @Probe(httpActionPath = "/actuator/health"),
        readinessProbe = @Probe(httpActionPath = "/actuator/health"),
        serviceType = ServiceType.NodePort,
        imagePullPolicy = ImagePullPolicy.Always)
@Log4j2
public class VarahamihirIdentityManagerLauncher extends SpringBootServletInitializer {


  @Autowired
  private ClientService clientService;
  @Autowired
  private TenantService tenantService;

  public static void main(String[] args) throws Exception {

    VarahamihirIdentityManagerLauncher launcher = new VarahamihirIdentityManagerLauncher();
    launcher
            .configure(new SpringApplicationBuilder(VarahamihirIdentityManagerLauncher.class))
            .run(args);
  }

  @PostConstruct
  public void initialize() {
    if (tenantService.count() == 0) {

      Tenant tenant = new Tenant();
      tenant.setDescription("Default tenant for the system");
      tenant.setDiscriminator(VarahamihirConstants.DEFAULT_TENANT);
      tenant.setName("Default Tenant");
      tenant.setDefaultValue(true);
      tenant.setExpiry(VarahamihirConstants.DEFAULT_ACCESS_TOKEN_VALIDITY);
      tenant.setRefreshExpiry(VarahamihirConstants.DEFAULT_REFRESH_TOKEN_VALIDITY);
      tenantService.save(tenant);
    }

    if (clientService.count() == 0) {
      VarahamihirClientDetails clientDetails = VarahamihirClientDetails.builder()
              .id(UUID.randomUUID())
              .clientId(VarahamihirConstants.DEFAULT_CLIENT)
              .clientSecret(VarahamihirConstants.DEFAULT_SECRET)
              .accessTokenValidity(VarahamihirConstants.DEFAULT_ACCESS_TOKEN_VALIDITY)
              .refreshTokenValidity(VarahamihirConstants.DEFAULT_REFRESH_TOKEN_VALIDITY)
              .authorities(Arrays.asList(Role.ADMIN.name(), Role.GUARDIAN.name(), Role.STUDENT.name()).stream().map(e -> new VarahamihirGrantedAuthority(e)).collect(Collectors.toSet()))
              .scope(Arrays.asList("read", "write").stream().collect(Collectors.toSet()))
              .autoApprove(true)
              .authorizedGrantTypes(MyConstants.AUTHORIZED_GRANT_TYPES)
              .build();
      clientService.save(clientDetails);
    }
//setupService.setup();
  }
//  @Bean
  public RouteLocator routes(RouteLocatorBuilder builder) {

    return builder.routes()
            .route(p -> p.path("/default/guardian/**").filters(f ->
                    f.hystrix(c -> c.setName("guardian").setFallbackUri("forward:/fallback"))).uri("lb://guardian"))
            .route(p -> p.path("/*/student/**").filters(f ->
                    f.hystrix(c -> c.setName("student").setFallbackUri("forward:/fallback"))).uri("lb://student"))
            .build();
  }

  @RequestMapping("/fallback")
  public ResponseEntity<List<String>> fallback() {
    System.out.println("fallback enabled");
    HttpHeaders headers = new HttpHeaders();
    headers.add("fallback", "true");
    return ResponseEntity.ok().headers(headers).body(Collections.emptyList());
  }
/*  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }*/

}