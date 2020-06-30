package com.avasthi.varahamihir.identityserver.configs;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class VarahaJWTReactiveAuthManager implements ReactiveAuthenticationManager {
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.just(authentication);
  }
}
