package com.avasthi.varahamihir.client.converters;

import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.common.utils.VarahamihirJWTBaseUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.function.Function;


public class VarahamihirJWTClientAuthConverters implements Function<ServerWebExchange, Mono<Authentication>> {

  final String bearerLowercase = "bearer";
  @Autowired
  private VarahamihirJWTBaseUtil jwtUtil;
  public VarahamihirJWTClientAuthConverters(VarahamihirJWTBaseUtil  jwtUtil) {
    this .jwtUtil = jwtUtil;
  }
  @Override
  public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {

    try {
      String authorizationHeader
              = VarahamihirJWTBaseUtil.getAuthorizationPayload(serverWebExchange);
      if (authorizationHeader != null) {

        String[] pieces = authorizationHeader.split(" ");
        if (pieces.length == 2
                && pieces[0].toLowerCase().equals(bearerLowercase)) {
          TokenClaims tokenClaims
                  = jwtUtil.retrieveTokenClaims(pieces[1]);
          return Mono.just(jwtUtil.getClientAuthenticationToken(tokenClaims));
        }
      }
    } catch (ParseException | JOSEException | BadJOSEException e) {
      e.printStackTrace();
    }
    return Mono.empty();
  }
}
