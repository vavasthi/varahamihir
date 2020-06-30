package com.avasthi.varahamihir.identityserver.resolvers;

import com.avasthi.varahamihir.common.exceptions.VarahamihirBaseException;
import com.avasthi.varahamihir.identityserver.configs.VarahamihirAuthenticationManager;
import com.avasthi.varahamihir.identityserver.configs.VarahamihirBasicAuthenticationManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class VarahamihirAuthenticationManagerResolver
        implements ReactiveAuthenticationManagerResolver<ServerWebExchange> {
  private ReactiveAuthenticationManager varahamihirAuthenticationManager;
  private ReactiveAuthenticationManager varahamihirBasicAuthenticationManager;

  public VarahamihirAuthenticationManagerResolver(VarahamihirAuthenticationManager varahamihirAuthenticationManager,
                                                  VarahamihirBasicAuthenticationManager varahamihirBasicAuthenticationManager) {
    this.varahamihirAuthenticationManager = varahamihirAuthenticationManager;
    this.varahamihirBasicAuthenticationManager = varahamihirBasicAuthenticationManager;

  }
  @Override
  public Mono<ReactiveAuthenticationManager> resolve(ServerWebExchange serverWebExchange) {

    return ServerWebExchangeMatchers.pathMatchers("/*/login").matches(serverWebExchange).filter((matchResult) -> {
      return matchResult.isMatch();
    }).flatMap((matchResult) -> {
      return Mono.just(varahamihirBasicAuthenticationManager);
    }).switchIfEmpty(Mono.just(varahamihirAuthenticationManager));

//    AntPathMatcher pathMatcher = new AntPathMatcher();
//    if (pathMatcher.match("/*/login", serverWebExchange.getRequest().getPath().toString())) {
//
//      return Mono.just(varahamihirBasicAuthenticationManager);
//    }
//    else if (pathMatcher.match("/**", serverWebExchange.getRequest().getPath().toString()) {
//      return Mono.just(varahamihirAuthenticationManager);
//    }
//    return null;
  }
}
