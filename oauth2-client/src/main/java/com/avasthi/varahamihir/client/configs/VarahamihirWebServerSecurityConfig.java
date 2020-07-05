package com.avasthi.varahamihir.client.configs;

import com.avasthi.varahamihir.client.filters.AuthorizationHeaderFilter;
import com.avasthi.varahamihir.client.filters.TenantHeaderFilter;
import com.avasthi.varahamihir.client.filters.VarahamihirJWTClientAuthWebFilter;
import com.avasthi.varahamihir.client.handlers.VarahamihirClientAuthenticationSuccessHandler;
import com.avasthi.varahamihir.client.services.VarahamihirReactiveAuthTokenUserDetailService;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.utils.VarahamihirJWTBaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableScheduling
@ComponentScan(basePackages = {"com.avasthi.varahamihir.common.utils", "com.avasthi.varahamihir.common.services"})
public class VarahamihirWebServerSecurityConfig {

  @Autowired
  private VarahamihirReactiveAuthTokenUserDetailService authTokenUserDetailService;
  @Autowired
  private VarahamihirJWTBaseUtil  jwtUtil;
  private static final String[] WHITELISTED_AUTH_URLS = {
          "/actuator/health",
          "/*/registration/*"
  };

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {


    AuthenticationWebFilter authenticationWebFilter
            = new AuthenticationWebFilter(new UserDetailsRepositoryReactiveAuthenticationManager(authTokenUserDetailService));
    authenticationWebFilter.setAuthenticationSuccessHandler(new VarahamihirClientAuthenticationSuccessHandler());
    return http.csrf().disable()
            .authorizeExchange()
            .pathMatchers(WHITELISTED_AUTH_URLS).permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .anyExchange().authenticated()
            .and()
          //  .addFilterAt(new AuthorizationHeaderFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(new TenantHeaderFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(new AuthorizationHeaderFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.FIRST)
            //        .addFilterAt(new TenantFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(new VarahamihirJWTClientAuthWebFilter(jwtUtil), SecurityWebFiltersOrder.HTTP_BASIC)
            .build();
  }


  @Bean
  public ServerAuthenticationEntryPoint unauthorizedEntryPoint() {

    return new ServerAuthenticationEntryPoint() {
      @Override
      public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        throw new UnauthorizedException(String.format("Not authorized for operation"));
      }
    };
  }
}
