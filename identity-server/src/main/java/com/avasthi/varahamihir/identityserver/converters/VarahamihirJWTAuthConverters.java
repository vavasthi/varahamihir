package com.avasthi.varahamihir.identityserver.converters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
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

  final String bearerLowercase = "bearer";
  @Autowired
  private VarahamihirJWTUtil jwtUtil;
  public VarahamihirJWTAuthConverters(VarahamihirJWTUtil jwtUtil) {
    this .jwtUtil = jwtUtil;
  }
  @Override
  public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {

    try {
      String authorizationHeader
              = VarahamihirJWTUtil.getAuthorizationPayload(serverWebExchange);
      String[] pieces = authorizationHeader.split(" ");
      if (pieces.length == 2
              && pieces[0].toLowerCase().equals(bearerLowercase)) {
        TokenClaims tokenClaims
                = jwtUtil.retrieveTokenClaims(pieces[1]);
        return Mono.just(jwtUtil.getAuthenticationToken(tokenClaims, pieces[1]));
      }
    } catch (ParseException | JOSEException | BadJOSEException e) {
      e.printStackTrace();
    }
    return Mono.error(new UnauthorizedException("Token could not be validated."));
  }
}
