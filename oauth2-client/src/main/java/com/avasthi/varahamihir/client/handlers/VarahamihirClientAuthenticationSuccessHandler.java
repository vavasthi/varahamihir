package com.avasthi.varahamihir.client.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class VarahamihirClientAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

  @Override
  public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
    ServerWebExchange exchange = webFilterExchange.getExchange();
    return webFilterExchange.getChain().filter(exchange);
  }

}
