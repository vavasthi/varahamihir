package com.avasthi.varahamihir.client.filters;

import com.avasthi.varahamihir.client.configs.VarahaJWTReactiveClientAuthManager;
import com.avasthi.varahamihir.client.converters.VarahamihirJWTClientAuthConverters;
import com.avasthi.varahamihir.common.utils.VarahamihirJWTBaseUtil;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// @Component
public class VarahamihirJWTClientAuthWebFilter implements WebFilter {

  private VarahamihirJWTBaseUtil jwtUtil;
  private final Function<ServerWebExchange, Mono<Authentication>> jwtAuthConverter;
  private final ReactiveAuthenticationManager reactiveAuthManager = new VarahaJWTReactiveClientAuthManager();
  private ServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();
  private ServerAuthenticationSuccessHandler authSuccessHandler = new WebFilterChainServerAuthenticationSuccessHandler();

  public VarahamihirJWTClientAuthWebFilter(VarahamihirJWTBaseUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
    this .jwtAuthConverter = new VarahamihirJWTClientAuthConverters(jwtUtil);
  }
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return this.getAuthMatcher().matches(exchange)
            .filter(matchResult -> matchResult.isMatch())
            .flatMap(matchResult -> this.jwtAuthConverter.apply(exchange))
            .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
            .flatMap(token -> authenticate(exchange, chain, token));
  }
  private ServerWebExchangeMatcher getAuthMatcher(){
    List<ServerWebExchangeMatcher> matchers = new ArrayList<>(2);
    matchers.add(new PathPatternParserServerWebExchangeMatcher("/**", HttpMethod.GET));

    ServerWebExchangeMatcher authMatcher = ServerWebExchangeMatchers.matchers(new OrServerWebExchangeMatcher(matchers));
    return authMatcher;
  }
  private Mono<Void> authenticate(ServerWebExchange exchange,
                                  WebFilterChain chain, Authentication token) {
    WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
    return this.reactiveAuthManager.authenticate(token)
            .flatMap(authentication -> onAuthSuccess(authentication, webFilterExchange));
  }

  private Mono<Void> onAuthSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
    ServerWebExchange exchange = webFilterExchange.getExchange();
    SecurityContextImpl securityContext = new SecurityContextImpl();
    securityContext.setAuthentication(authentication);
    return this.securityContextRepository.save(exchange, securityContext)
            .then(this.authSuccessHandler
                    .onAuthenticationSuccess(webFilterExchange, authentication))
            .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
  }}
