package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.identityserver.filters.*;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import org.springframework.security.web.server.context.ReactorContextWebFilter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableScheduling
public class VarahamihirWebServerSecurityConfig {

  @Autowired
  private VarahamihirAuthenticationManager authenticationManager;
  @Autowired
  private VarahamihirBasicAuthenticationManager basicAuthenticationManager;
  @Autowired
  private ServerSecurityContextRepository securityContextRepository;
  @Autowired
  private VarahamihirJWTUtil varahamihirJWTUtil;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    AuthenticationWebFilter authenticationWebFilter
            = new AuthenticationWebFilter(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository()))
    return http
            .authorizeExchange()
            .pathMatchers("/actuator/health").permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/*/login").permitAll()
            .pathMatchers("/*/registration/*").permitAll()
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
            .addFilterAfter(new AuthorizationHeaderFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
            .addFilterAfter(new TenantHeaderFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
            .addFilterAfter(new TenantFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
            .addFilterAt(new LoginWebFilter(basicAuthenticationManager,
                            authenticationManager,
                            securityContextRepository,
                            varahamihirJWTUtil),
                    SecurityWebFiltersOrder.AUTHENTICATION)
            // .addFilterBefore(new CurrentUserExtractionFilter(), SecurityWebFiltersOrder.LAST)
//            .addFilterAfter(new CurrentUserExtractionFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
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
