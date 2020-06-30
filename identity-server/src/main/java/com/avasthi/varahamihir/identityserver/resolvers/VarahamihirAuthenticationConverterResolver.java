package com.avasthi.varahamihir.identityserver.resolvers;

import com.avasthi.varahamihir.identityserver.configs.VarahamihirAuthenticationManager;
import com.avasthi.varahamihir.identityserver.configs.VarahamihirBasicAuthenticationManager;
import com.avasthi.varahamihir.identityserver.converters.VarahamihirBasicAuthenticationConverter;
import com.avasthi.varahamihir.identityserver.converters.VarahamihirJwtAuthenticationConverter;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class VarahamihirAuthenticationConverterResolver {
  private ServerAuthenticationConverter varahamihirJwtServerAuthenticationConverter;
  private ServerAuthenticationConverter varahamihirBasicServerAuthenticationConverter;

  public VarahamihirAuthenticationConverterResolver(VarahamihirJWTUtil jwtUtil) {
    this.varahamihirJwtServerAuthenticationConverter
            = new VarahamihirJwtAuthenticationConverter(jwtUtil);
    this.varahamihirBasicServerAuthenticationConverter = new VarahamihirBasicAuthenticationConverter();
  }
  public Mono<ServerAuthenticationConverter> resolve(ServerWebExchange serverWebExchange) {

    return ServerWebExchangeMatchers.pathMatchers("/*/login").matches(serverWebExchange).filter((matchResult) -> {
      return matchResult.isMatch();
    }).flatMap((matchResult) -> {
      return Mono.just(varahamihirBasicServerAuthenticationConverter);
    }).switchIfEmpty(Mono.just(varahamihirJwtServerAuthenticationConverter));

  }
}
