package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableScheduling
public class VarahamihirWebServerSecurityConfig {

  @Autowired
  private VarahamihirAuthenticationManager authenticationManager;
  @Autowired
  private VarahamihirSecurityContextRepository securityContextRepository;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
            .authorizeExchange()
            .pathMatchers("/actuator/health").permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/default/login").permitAll()
            .anyExchange().authenticated()
            .and()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .exceptionHandling()
            .authenticationEntryPoint((swe, e) -> {
              return Mono.fromRunnable(() -> {
                swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
              });
            }).accessDeniedHandler((swe, e) -> {
              return Mono.fromRunnable(() -> {
                swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
              });
            }).and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .build();
  }

/*  @Override
  protected void configure(HttpSecurity http) throws Exception {
/*        http.addFilterAfter(new CurrentUserExtractionFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new TenantHeaderFilter(), CurrentUserExtractionFilter.class);*/

/*        http.addFilterBefore(new TenantHeaderFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new AuthenticationParameterExtractionFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new SufficientAuthenticationParametersPresentFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);*/



  private String[] actuatorEndpoints() {
    return new String[]{
            VarahamihirConstants.AUTOCONFIG_ENDPOINT,
            VarahamihirConstants.BEANS_ENDPOINT,
            VarahamihirConstants.CONFIGPROPS_ENDPOINT,
            VarahamihirConstants.ENV_ENDPOINT,
            VarahamihirConstants.MAPPINGS_ENDPOINT,
            VarahamihirConstants.METRICS_ENDPOINT,
            VarahamihirConstants.SHUTDOWN_ENDPOINT,
            VarahamihirConstants.HEALTH_ENDPOINT,
    };
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
