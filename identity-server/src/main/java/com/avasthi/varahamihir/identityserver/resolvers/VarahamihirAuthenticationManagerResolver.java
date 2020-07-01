package com.avasthi.varahamihir.identityserver.resolvers;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class VarahamihirAuthenticationManagerResolver implements ReactiveAuthenticationManagerResolver<ServerWebExchange> {
  private ReactiveAuthenticationManager clientReactiveAuthenticationManager;
  private ReactiveAuthenticationManager userReactiveAuthenticationManager;

  public VarahamihirAuthenticationManagerResolver(ReactiveAuthenticationManager clientReactiveAuthenticationManager,
                                                  ReactiveAuthenticationManager userReactiveAuthenticationManager) {
    this.clientReactiveAuthenticationManager = clientReactiveAuthenticationManager;
    this.userReactiveAuthenticationManager = userReactiveAuthenticationManager;

  }

  @Override
  public Mono<ReactiveAuthenticationManager> resolve(ServerWebExchange serverWebExchange) {

    return ServerWebExchangeMatchers
            .pathMatchers("/*/login", "/*/registration/user", "/*/oauth/token")
            .matches(serverWebExchange).filter((matchResult) -> {
      return matchResult.isMatch();
    }).flatMap((matchResult) -> {
      return Mono.just(clientReactiveAuthenticationManager);
    }).switchIfEmpty(Mono.just(userReactiveAuthenticationManager));

  }
}
