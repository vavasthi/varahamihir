package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.pojos.TokenClaims;
import com.avasthi.varahamihir.common.pojos.VarahamihirOAuth2Principal;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Log4j2
//@Configuration
public class VarahamihirSecurityContextRepository implements ServerSecurityContextRepository {

  public static final String SECURITY_CONTEXT_ATTRIBUTE = "SECURITY_CONTEXT";
  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;
  @Autowired
  private VarahamihirAuthenticationManager authenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
    return swe.getSession().doOnNext((session) -> {
      if (sc == null) {
        log.error(String.format("Saving Security Context is %s and session id is %s",
                (sc == null ? "null" : sc.toString()),
                session.getId()));
        session.getAttributes().remove(SECURITY_CONTEXT_ATTRIBUTE);
      } else {
        log.error(String.format("Saving Security Context is %s and session id is %s",
                (sc == null ? "null" : sc.toString()),
                session.getId()));
        session.getAttributes().put(SECURITY_CONTEXT_ATTRIBUTE, sc);
      }

    }).flatMap((session) -> {
      return session.changeSessionId();
    });
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange swe) {
    return swe.getSession().map(WebSession::getAttributes).flatMap((attrs) -> {
      SecurityContext context = (SecurityContext)attrs.get(SECURITY_CONTEXT_ATTRIBUTE);
      log.error(String.format("Loading Security Context is %s and session id is %s",
              (context == null ? "null" : context.toString()),
              attrs.get("sessioId")));
      return Mono.justOrEmpty(context);
    });

/*    ServerHttpRequest request = swe.getRequest();
    String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith("Bearer ") && authHeader.contains(" ")) {
      // Split the auth header at space and the second token is the authToken.
      String authToken = authHeader.split(" ")[1];

      try {
        TokenClaims tokenClaims = varahamihirJwtUtil.retrieveTokenClaims(authToken);
        VarahamihirOAuth2Principal principal = getPrincipal(tokenClaims, authToken);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, authToken);
        return this.authenticationManager.authenticate(auth).map((authentication) -> {
          return new SecurityContextImpl(authentication);
        });
      } catch (ParseException|JOSEException|BadJOSEException e) {
        return Mono.error(new UnauthorizedException("The auth token is invalid."));
      }
    } else {
      return Mono.empty();
    }*/
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
