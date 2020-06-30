package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.common.pojos.VarahamihirOAuth2Principal;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component("varahamihirAuthenticationManager")
@Primary
public class VarahamihirAuthenticationManager implements ReactiveAuthenticationManager {
  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;
  @Autowired
  private UserService userService;

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    try {
      VarahamihirOAuth2Principal principal = (VarahamihirOAuth2Principal)authentication.getPrincipal();
      TokenClaims tokenClaims = varahamihirJwtUtil.retrieveTokenClaims(principal.getAuthToken());
      if (!varahamihirJwtUtil.validateToken(principal.getAuthToken())) {
        return Mono.empty();
      }
      switch(tokenClaims.getSubjectType()) {
        case USER:
          return userService.findByUsername(tokenClaims.getSubject())
                  .flatMap(u -> {
                    final Set<GrantedAuthority> actualAuthorities = new HashSet<>();
                    Set<GrantedAuthority> requestedAuthorities = tokenClaims.getAuthorities();
                    Set<String> storedAuthorities = u.getGrantedAuthorities();
                    for (GrantedAuthority rga : requestedAuthorities) {
                      if (storedAuthorities.contains(rga.getAuthority())) {
                        actualAuthorities.add(rga);
                      }
                    }
                    Authentication auth = new UsernamePasswordAuthenticationToken(principal,
                            authToken,
                            actualAuthorities);
                    return Mono.just(auth);
                  })
                  .switchIfEmpty(Mono.error(new UnauthorizedException(String.format("User %s not found.", tokenClaims.getSubject()))));
        case CLIENT:
          Authentication auth = new UsernamePasswordAuthenticationToken(principal,
                  authToken,
                  Arrays.asList(new VarahamihirGrantedAuthority(Role.CLIENT.name())));
          return Mono.just(auth);
      }
      //SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (Exception e) {
      e.printStackTrace();
      return Mono.error(e);
    }
    return Mono.error(new UnauthorizedException("Unreachable place."));
  }
}
