package com.avasthi.varahamihir.identityserver.filters;

import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.identityserver.configs.VarahamihirAuthenticationManager;
import com.avasthi.varahamihir.identityserver.configs.VarahamihirBasicAuthenticationManager;
import com.avasthi.varahamihir.identityserver.converters.VarahamihirBasicAuthenticationConverter;
import com.avasthi.varahamihir.identityserver.resolvers.VarahamihirAuthenticationConverterResolver;
import com.avasthi.varahamihir.identityserver.resolvers.VarahamihirAuthenticationManagerResolver;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.*;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@Component
public class LoginWebFilter extends VarahamihirAbstractFilter implements WebFilter{

  private final ReactiveAuthenticationManagerResolver<ServerWebExchange> authenticationManagerResolver;
  private ServerAuthenticationSuccessHandler authenticationSuccessHandler
          = new ServerAuthenticationSuccessHandler() {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

      log.error("Reached here");
      return Mono.empty();
    }
  };
  private ServerAuthenticationConverter authenticationConverter = new VarahamihirBasicAuthenticationConverter();
  private ServerAuthenticationFailureHandler authenticationFailureHandler
          = new ServerAuthenticationEntryPointFailureHandler(new HttpBasicServerAuthenticationEntryPoint());
  private ServerSecurityContextRepository securityContextRepository;
  private ServerWebExchangeMatcher requiresAuthenticationMatcher
          = ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/**");
  private VarahamihirAuthenticationConverterResolver varahamihirAuthenticationConverterResolver;

  public LoginWebFilter(VarahamihirBasicAuthenticationManager basicAuthenticationManager,
                        VarahamihirAuthenticationManager authenticationManager,
                        ServerSecurityContextRepository serverSecurityContextRepository,
                        VarahamihirJWTUtil jwtUtil) {
    Assert.notNull(authenticationManager, "authenticationResolver cannot be null");
    this.authenticationManagerResolver
            = new VarahamihirAuthenticationManagerResolver(authenticationManager, basicAuthenticationManager);
    this.securityContextRepository = serverSecurityContextRepository;
    varahamihirAuthenticationConverterResolver = new VarahamihirAuthenticationConverterResolver(jwtUtil);
  }

  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    logSecurityContext(LoginWebFilter.class);

    return varahamihirAuthenticationConverterResolver.resolve(exchange)
            .flatMap(converter -> converter.convert(exchange)
                    .flatMap(auth -> {
                      LoginWebFilter.this.authenticate(exchange, chain, auth);
                      return chain.filter(exchange);
                    }));
  }

  private Mono<Void> authenticate(ServerWebExchange exchange, WebFilterChain chain, Authentication auth) {
    WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
    Mono<ReactiveAuthenticationManager> reactiveAuthenticationManagerMono
            = this.authenticationManagerResolver.resolve(exchange);
    Optional<ReactiveAuthenticationManager> reactiveAuthenticationManagerOptional
            = reactiveAuthenticationManagerMono.blockOptional();
    if (reactiveAuthenticationManagerOptional.isPresent()) {

      ReactiveAuthenticationManager reactiveAuthenticationManager
              = reactiveAuthenticationManagerOptional.get();
      Mono<Authentication> authenticationMono = reactiveAuthenticationManager.authenticate(auth);
      authenticationMono.flatMap(authentication -> {
        if (authentication.isAuthenticated()) {
          return authenticationMono;
        }
        else {
          return Mono.error(new UnauthorizedException("Failure in authentication"));
        }
      });
/*      return reactiveAuthenticationManagerMono
              .flatMap((authenticationManager) -> {
                log.error("2Found a userful authmanager.");
                return authenticationManager.authenticate(auth);
              })
              .switchIfEmpty(Mono.error(new IllegalStateException("No provider found for " + auth.getClass())))
              .flatMap((authentication) -> {
                log.error("4Found a userful authmanager.");
                return this.onAuthenticationSuccess(authentication, webFilterExchange);
              })
              .onErrorResume(AuthenticationException.class, (e) -> {
                log.error("5Found a userful authmanager.");
                e.printStackTrace();
                return this.authenticationFailureHandler.onAuthenticationFailure(webFilterExchange, e);
              });*/
    }
    return Mono.error(new UnauthorizedException("ReactiveAuthenticationManager not found."));
  }

  protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
    ServerWebExchange exchange = webFilterExchange.getExchange();
    SecurityContextImpl securityContext = new SecurityContextImpl();
    securityContext.setAuthentication(authentication);
    return this.securityContextRepository
            .save(exchange, securityContext)
            .then(this.authenticationSuccessHandler.onAuthenticationSuccess(webFilterExchange, authentication))
            .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
  }

  public void setServerAuthenticationConverter(ServerAuthenticationConverter authenticationConverter) {
    Assert.notNull(authenticationConverter, "authenticationConverter cannot be null");
    this.authenticationConverter = authenticationConverter;
  }

  public void setAuthenticationFailureHandler(ServerAuthenticationFailureHandler authenticationFailureHandler) {
    Assert.notNull(authenticationFailureHandler, "authenticationFailureHandler cannot be null");
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  public void setRequiresAuthenticationMatcher(ServerWebExchangeMatcher requiresAuthenticationMatcher) {
    Assert.notNull(requiresAuthenticationMatcher, "requiresAuthenticationMatcher cannot be null");
    this.requiresAuthenticationMatcher = requiresAuthenticationMatcher;
  }

}

