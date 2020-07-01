package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.common.pojos.VarahamihirOAuth2Principal;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class VarahaJWTReactiveAuthManager implements ReactiveAuthenticationManager {
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    VarahamihirOAuth2Principal principal
            = (VarahamihirOAuth2Principal) authentication.getPrincipal();
    return Mono.just(new UsernamePasswordAuthenticationToken(principal,
            principal.getAuthToken(),
            principal.getGrantedAuthorities().stream().map(s -> new VarahamihirGrantedAuthority(s)).collect(Collectors.toSet())));
  }
}
