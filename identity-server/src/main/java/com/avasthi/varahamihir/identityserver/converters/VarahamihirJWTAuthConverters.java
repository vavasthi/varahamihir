package com.avasthi.varahamihir.identityserver.converters;

import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.function.Function;


public class VarahamihirJWTAuthConverters implements Function<ServerWebExchange, Mono<Authentication>> {
  @Autowired
  private VarahamihirJWTUtil jwtUtil;
  public VarahamihirJWTAuthConverters(VarahamihirJWTUtil jwtUtil) {
    this .jwtUtil = jwtUtil;
  }
  @Override
  public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {

    try {
      TokenClaims tokenClaims
              = jwtUtil.retrieveTokenClaims(VarahamihirJWTUtil.getAuthorizationPayload(serverWebExchange));
      return Mono.just(jwtUtil.getAuthenticationToken(tokenClaims));
    } catch (ParseException | JOSEException | BadJOSEException e) {
      e.printStackTrace();
    }
    return Mono.error(new UnauthorizedException("Token could not be validated."));
  }
}
