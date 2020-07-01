package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AuthenticateRolePermission;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.*;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import com.avasthi.varahamihir.identityserver.services.ClientService;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class TokenEndpoint {
  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;

  @Autowired
  @Qualifier(value = "passwordEncoder")
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ClientService clientService;

  @Autowired
  private UserService userService;

  @RequestMapping(value = VarahamihirConstants.V1_TOKEN_ENDPOINT,
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Mono<VarahamihirAuthResponse> login(ServerWebExchange exchange,
                                             Authentication authentication) {
    return tokenRequest(exchange, authentication).flatMap(tokenRequest -> {

    switch(tokenRequest.getGrantType()) {
      case client_credentials:
        return clientCredential(tokenRequest);
      case password:
        return password(tokenRequest);
    }
      return Mono.empty();
    });
  }
  private Mono<TokenRequest> tokenRequest(ServerWebExchange exchange, Authentication authentication) {
    VarahamihirUserDetails ud = ((VarahamihirUserDetails)authentication.getPrincipal());
    return exchange.getFormData().flatMap( mvm -> {
      return Mono.just(TokenRequest.builder()
              .username(mvm.getFirst("username"))
              .password(mvm.getFirst("password"))
              .audience(mvm.getFirst("audience"))
              .clientId(ud.getUsername())
              .clientSecret(ud.getPassword())
              .grantType(TokenRequest.GrantType.valueOf(mvm.getFirst("grant_type")))
              .build());
    }).switchIfEmpty(Mono.empty());
  }
  private Mono<VarahamihirAuthResponse> clientCredential(TokenRequest tokenRequest) {

    Mono<VarahamihirClientDetails> monoClientDetails
            = clientService.findByClientId(tokenRequest.getClientId());
    return monoClientDetails.flatMap(clientDetails -> {

      if (passwordEncoder.matches(tokenRequest.getClientSecret(), clientDetails.getClientSecret())
              || tokenRequest.getClientSecret().equals(clientDetails.getClientSecret())) {
          return Mono.subscriberContext()
                  .flatMap(tenantDiscriminatorContext -> {
                    Tenant tenant
                            = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
                    VarahamihirAuthResponse tokenResponse = null;
                    try {

                      tokenResponse = VarahamihirAuthResponse.builder()
                              .auth_token(varahamihirJwtUtil.generateToken(tenant,
                                      clientDetails,
                                      VarahamihirTokenType.ACCESS_TOKEN,
                                      tokenRequest.getAudience()))
                              .expiry(clientDetails.getAccessTokenValidity())
                              .refreshToken(varahamihirJwtUtil.generateToken(tenant,
                                      clientDetails,
                                      VarahamihirTokenType.REFRESH_TOKEN,
                                      tokenRequest.getAudience()))
                              .refreshTokenExpiry(clientDetails.getRefreshTokenValidity())
                              .tokenType(TokenResponse.TokenType.Bearer.name())
                              .scope(StringUtils.join(clientDetails.getScope(), ","))
                              .build();
                    } catch (JOSEException e) {
                      return Mono.error(e);
                    }
                    return Mono.just(tokenResponse);
                  })
                  .switchIfEmpty(Mono.error(new UnauthorizedException("No tenant in context.")));

      }
      else {

        return Mono.error(new UnauthorizedException(String.format("Password mismatch for client %s", clientDetails.getClientId())));
      }
    }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("Password mismatch for client %s", tokenRequest.getClientId()))));
  }
  private Mono<VarahamihirAuthResponse> password(TokenRequest tokenRequest) {

    Mono<VarahamihirClientDetails> monoClientDetails
            = clientService.findByClientId(tokenRequest.getClientId());
    return monoClientDetails.flatMap(clientDetails -> {

      if (passwordEncoder.matches(tokenRequest.getClientSecret(), clientDetails.getClientSecret())
              ||tokenRequest.getClientSecret().equals(clientDetails.getClientSecret())) {
        return Mono.subscriberContext()
                .flatMap(tenantDiscriminatorContext -> {
                  Tenant tenant
                          = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
                  Mono<User> userMono = userService.findByUsername(tokenRequest.getUsername());
                  return userMono.flatMap(user -> {
                    if (passwordEncoder.matches(tokenRequest.getPassword(), user.getPassword())) {

                      VarahamihirAuthResponse tokenResponse = null;
                      try {

                        tokenResponse = VarahamihirAuthResponse.builder()
                                .auth_token(varahamihirJwtUtil.generateToken(tenant,
                                        user,
                                        VarahamihirTokenType.ACCESS_TOKEN,
                                        tokenRequest.getAudience()))
                                .expiry(clientDetails.getAccessTokenValidity())
                                .refreshToken(varahamihirJwtUtil.generateToken(tenant,
                                        user,
                                        VarahamihirTokenType.REFRESH_TOKEN,
                                        tokenRequest.getAudience()))
                                .refreshTokenExpiry(clientDetails.getRefreshTokenValidity())
                                .tokenType(TokenResponse.TokenType.Bearer.name())
                                .scope(StringUtils.join(clientDetails.getScope(), ","))
                                .build();
                      } catch (JOSEException e) {
                        return Mono.error(e);
                      }
                      return Mono.just(tokenResponse);
                    }
                    else {
                      return Mono.error(new UnauthorizedException(String.format("Invalid password for user %s.", tokenRequest.getUsername())));
                    }
                  }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("User %s is not found.", tokenRequest.getUsername()))));
                })
                .switchIfEmpty(Mono.error(new UnauthorizedException("No tenant in context.")));

      }
      else {

        return Mono.error(new UnauthorizedException(String.format("Password mismatch for client %s", clientDetails.getClientId())));
      }
    }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("Password mismatch for client %s", tokenRequest.getClientId()))));
  }
}
