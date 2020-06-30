package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.ExceptionResponse;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.exceptions.VarahamihirBaseException;
import com.avasthi.varahamihir.identityserver.filters.AuthorizationHeaderFilter;
import com.avasthi.varahamihir.identityserver.filters.TenantFilter;
import com.avasthi.varahamihir.identityserver.filters.TenantHeaderFilter;
import com.avasthi.varahamihir.identityserver.filters.VarahamihirJWTAuthWebFilter;
import com.avasthi.varahamihir.identityserver.handlers.VarahamihirAuthenticationSuccessHandler;
import com.avasthi.varahamihir.identityserver.services.VarahamihirReactiveUserDetailService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableScheduling
public class VarahamihirWebServerSecurityConfig {

  @Autowired
  private VarahamihirReactiveUserDetailService userDetailService;

  @Autowired
  private VarahamihirJWTUtil jwtUtil;
  private static final String[] WHITELISTED_AUTH_URLS = {
          "/actuator/health",
          "/*/login",
          "/*/registration"
  };
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {


    AuthenticationWebFilter authenticationWebFilter
            = new AuthenticationWebFilter(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailService));
    authenticationWebFilter.setAuthenticationSuccessHandler(new VarahamihirAuthenticationSuccessHandler());
    return http.csrf().disable()
            .addFilterAt(new AuthorizationHeaderFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(new TenantHeaderFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAfter(new TenantFilter(), SecurityWebFiltersOrder.FIRST)
            .authorizeExchange()
            .pathMatchers(WHITELISTED_AUTH_URLS).permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .and()
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.FIRST)
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(new VarahamihirJWTAuthWebFilter(jwtUtil), SecurityWebFiltersOrder.HTTP_BASIC)
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
