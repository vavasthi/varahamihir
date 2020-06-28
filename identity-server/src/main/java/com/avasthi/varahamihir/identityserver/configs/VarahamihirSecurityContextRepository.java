package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.identityserver.configs.VarahamihirAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class VarahamihirSecurityContextRepository implements ServerSecurityContextRepository {

  @Autowired
  private VarahamihirAuthenticationManager authenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange swe) {
    ServerHttpRequest request = swe.getRequest();
    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String authToken = authHeader.substring(7);
      Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
      return this.authenticationManager.authenticate(auth).map((authentication) -> {
        return new SecurityContextImpl(authentication);
      });
    } else {
      return Mono.empty();
    }
  }

}
