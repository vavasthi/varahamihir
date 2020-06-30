package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.identityserver.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("varahamihirBasicAuthenticationManager")
public class VarahamihirBasicAuthenticationManager implements ReactiveAuthenticationManager {
  @Autowired
  private ClientService clientService;

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    String clientId = authentication.getName();
    return Mono.just(authentication);
  }
}
