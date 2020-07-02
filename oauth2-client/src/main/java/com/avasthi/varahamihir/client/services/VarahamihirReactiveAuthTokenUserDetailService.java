package com.avasthi.varahamihir.client.services;

import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.common.pojos.VarahamihirUserDetails;
import com.avasthi.varahamihir.common.utils.VarahamihirJWTBaseUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
public class VarahamihirReactiveAuthTokenUserDetailService
        implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

  @Value("${varahamihir.dummy.password:IReallyReallyWantReallyRallyWant}")
  protected String dummyPassword;

  @Autowired
  private VarahamihirJWTBaseUtil jwtBaseUtil;

  @Override
  public Mono<UserDetails> updatePassword(UserDetails userDetails, String s) {
    return null;
  }

  @Override
  public Mono<UserDetails> findByUsername(String token) {
    try {

      TokenClaims tokenClaims = jwtBaseUtil.retrieveTokenClaims(token);
      return Mono.just(VarahamihirUserDetails.builder()
              .credentialsNonExpired(true)
              .accountNonLocked(true)
              .grantedAuthorities(tokenClaims.getAuthorities())
              .accountMonExpired(true)
              .tenantId(tokenClaims.getTenantId())
              .password(dummyPassword)
              .userName(tokenClaims.getSubject())
              .build());
    } catch (ParseException | JOSEException | BadJOSEException e) {
      Mono.error(new UnauthorizedException(String.format("Token could not parsed and validated.")));
    }
    return null;
  }
}
