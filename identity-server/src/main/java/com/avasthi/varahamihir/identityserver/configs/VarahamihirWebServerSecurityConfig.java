package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.identityserver.filters.AuthorizationHeaderFilter;
import com.avasthi.varahamihir.identityserver.filters.TenantFilter;
import com.avasthi.varahamihir.identityserver.filters.TenantHeaderFilter;
import com.avasthi.varahamihir.identityserver.filters.VarahamihirJWTAuthWebFilter;
import com.avasthi.varahamihir.identityserver.handlers.VarahamihirAuthenticationSuccessHandler;
import com.avasthi.varahamihir.identityserver.resolvers.VarahamihirAuthenticationManagerResolver;
import com.avasthi.varahamihir.identityserver.services.VarahamihirReactiveClientUserDetailService;
import com.avasthi.varahamihir.identityserver.services.VarahamihirReactiveUserDetailService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
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
public class VarahamihirWebServerSecurityConfig {

  @Autowired
  @Qualifier(value = "varahamihirReactiveUserDetailService")
  private VarahamihirReactiveUserDetailService userDetailService;
  @Autowired
  @Qualifier(value = "varahamihirReactiveClientUserDetailService")
  private VarahamihirReactiveClientUserDetailService clientUserDetailService;

  @Autowired
  private VarahamihirJWTUtil jwtUtil;
  private static final String[] WHITELISTED_AUTH_URLS = {
          "/actuator/**",
          "/*/login",
          "/*/registration/*"
  };
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {


    AuthenticationWebFilter authenticationWebFilter
            = new AuthenticationWebFilter(new VarahamihirAuthenticationManagerResolver(new UserDetailsRepositoryReactiveAuthenticationManager(clientUserDetailService),
            new UserDetailsRepositoryReactiveAuthenticationManager(userDetailService)));
    authenticationWebFilter.setAuthenticationSuccessHandler(new VarahamihirAuthenticationSuccessHandler());
    return http.csrf().disable()
            .authorizeExchange()
            .pathMatchers(WHITELISTED_AUTH_URLS).permitAll()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(new TenantHeaderFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(new AuthorizationHeaderFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(new TenantFilter(), SecurityWebFiltersOrder.FIRST)
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.FIRST)
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
