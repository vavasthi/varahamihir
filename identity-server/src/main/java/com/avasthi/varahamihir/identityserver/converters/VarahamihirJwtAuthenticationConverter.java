package com.avasthi.varahamihir.identityserver.converters;

import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.common.pojos.VarahamihirOAuth2Principal;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VarahamihirJwtAuthenticationConverter
        implements Function<ServerWebExchange, Mono<Authentication>>, ServerAuthenticationConverter {
  public static final String BASIC = "Basic ";

  private final VarahamihirJWTUtil varahamihirJWTUtil;
  public VarahamihirJwtAuthenticationConverter(VarahamihirJWTUtil varahamihirJWTUtil) {
    this.varahamihirJWTUtil = varahamihirJWTUtil;
  }

  public Mono<Authentication> apply(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith("Bearer ") && authHeader.contains(" ")) {
      // Split the auth header at space and the second token is the authToken.
      String authToken = authHeader.split(" ")[1];

      try {
        TokenClaims tokenClaims = varahamihirJWTUtil.retrieveTokenClaims(authToken);
        VarahamihirOAuth2Principal principal = getPrincipal(tokenClaims, authToken);
        return Mono.just(new UsernamePasswordAuthenticationToken(principal, authToken));
      } catch (ParseException | JOSEException | BadJOSEException e) {
        return Mono.error(new UnauthorizedException("The auth token is invalid."));
      }
    } else {
      return Mono.empty();
    }
  }

  @Override
  public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
    return apply(serverWebExchange);
  }

  private VarahamihirOAuth2Principal getPrincipal(TokenClaims tokenClaims, String authToken) {
    return VarahamihirOAuth2Principal.builder()
            .authToken(authToken)
            .subjectType(tokenClaims.getSubjectType())
            .tokenType(tokenClaims.getTokenType())
            .username(tokenClaims.getSubject())
            .grantedAuthorities(tokenClaims.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toSet()))
            .build();
  }

}
