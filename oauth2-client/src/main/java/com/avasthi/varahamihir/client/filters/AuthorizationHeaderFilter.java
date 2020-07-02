package com.avasthi.varahamihir.client.filters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.filters.VarahamihirAbstractFilter;
import com.avasthi.varahamihir.common.pojos.AuthorizationHeaderValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


//@Component
public class AuthorizationHeaderFilter extends VarahamihirAbstractFilter implements WebFilter {
  public static final String TENANT_HEADER = "X-tenant";
  private static final String defaultClient = "supersecretclient";
  private static final String defaultSecret = "supersecretclient123";

  @Value("${tutorial.default.tenant.discriminator:default}")
  private String defaultDiscriminator;

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

    String authorizationHeaderValue
            = serverWebExchange.getRequest().getHeaders().getFirst(VarahamihirConstants.AUTHORIZATION_HEADER_NAME);
    if (authorizationHeaderValue == null) {
      return Mono.error(new UnauthorizedException("Authorization header is not present."));
    }
    authorizationHeaderValue = authorizationHeaderValue.trim();
    String[] headerPieces = authorizationHeaderValue.split(" ");
    String authType = headerPieces[0].toLowerCase();
    String authToken = headerPieces[1];
    if (authType.equals("bearer")) {

      return webFilterChain
              .filter(serverWebExchange)
              .subscriberContext(context -> context.put(VarahamihirConstants.AUTHORIZATION_HEADER_IN_CONTEXT,
                      AuthorizationHeaderValues.builder()
                              .authToken(authToken)
                              .authType(AuthorizationHeaderValues.AuthType.Bearer)
                              .build()));
    } else if (authType.equals("basic")) {
      String[] decodedToken = new String(Base64Utils.decode(authToken.getBytes())).split(":");
      String username = decodedToken[0];
      String password = decodedToken[1];
      return webFilterChain
              .filter(serverWebExchange)
              .subscriberContext(context -> context.put(VarahamihirConstants.AUTHORIZATION_HEADER_IN_CONTEXT,
                      AuthorizationHeaderValues.builder()
                              .authToken(authToken)
                              .username(username)
                              .password(password)
                              .clientId(username)
                              .authType(AuthorizationHeaderValues.AuthType.Basic)
                              .build()));
    } else {

      return unauthorizedException(serverWebExchange, "The authorization header is neither of type basic nor value.");
    }
  }
}
