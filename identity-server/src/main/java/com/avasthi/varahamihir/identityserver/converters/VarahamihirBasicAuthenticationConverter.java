package com.avasthi.varahamihir.identityserver.converters;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.function.Function;

public class VarahamihirBasicAuthenticationConverter
        implements Function<ServerWebExchange, Mono<Authentication>>,
        ServerAuthenticationConverter {
  public static final String BASIC = "Basic ";

  public VarahamihirBasicAuthenticationConverter() {
  }

  public Mono<Authentication> apply(ServerWebExchange exchange) {
    ServerHttpRequest request = exchange.getRequest();
    String authorization = request.getHeaders().getFirst("Authorization");
    if (!StringUtils.startsWithIgnoreCase(authorization, "basic ")) {
      return Mono.empty();
    } else {
      String credentials
              = (authorization.length() <= "Basic ".length()
              ? ""
              : authorization.substring("Basic ".length(), authorization.length()));
      byte[] decodedCredentials = this.base64Decode(credentials);
      String decodedAuthz = new String(decodedCredentials);
      String[] userParts = decodedAuthz.split(":", 2);
      if (userParts.length != 2) {
        return Mono.empty();
      } else {
        String username = userParts[0];
        String password = userParts[1];
        return Mono.just(new UsernamePasswordAuthenticationToken(username, password));
      }
    }
  }

  private byte[] base64Decode(String value) {
    try {
      return Base64.getDecoder().decode(value);
    } catch (Exception var3) {
      return new byte[0];
    }
  }

  @Override
  public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
    return apply(serverWebExchange);
  }
}
