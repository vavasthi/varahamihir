package com.avasthi.varahamihir.client.configs;

import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.common.pojos.VarahamihirOAuth2Principal;
import com.avasthi.varahamihir.common.pojos.VarahamihirTokenPrincipal;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

public class VarahaJWTReactiveClientAuthManager implements ReactiveAuthenticationManager {
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    VarahamihirTokenPrincipal principal
            = (VarahamihirTokenPrincipal) authentication.getPrincipal();
    return Mono.just(new UsernamePasswordAuthenticationToken(principal,
            principal.getCredentials(),
            principal.getGrantedAuthorities().stream().map(s -> new VarahamihirGrantedAuthority(s)).collect(Collectors.toSet())));
  }
}
