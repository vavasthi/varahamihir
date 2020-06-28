package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirTokenEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
@Component
public class VarahamihirAuthenticationManager implements ReactiveAuthenticationManager {
  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();

    try {
      if (!varahamihirJwtUtil.validateToken(authToken)) {
        return Mono.empty();
      }
       TokenClaims tokenClaims = varahamihirJwtUtil.retrieveTokenClaims(authToken);
      return Mono.just(new UsernamePasswordAuthenticationToken(tokenClaims.getSubject(), null, tokenClaims.getAuthorities()));
    } catch (Exception e) {
      return Mono.empty();
    }
  }
  @Bean(name = "passwordEncoder")
  public PasswordEncoder userPasswordEncoder() {
    return new SCryptPasswordEncoder();
  }

  @Bean(name = "tokenEncoder")
  public PasswordEncoder tokenEncoder() {
    return new VarahamihirTokenEncoder();
  }
}
