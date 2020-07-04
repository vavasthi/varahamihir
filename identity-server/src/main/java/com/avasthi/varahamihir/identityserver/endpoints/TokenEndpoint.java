package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AuthenticateRolePermission;
import com.avasthi.varahamihir.common.annotations.RefreshRolePermission;
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
  @AuthenticateRolePermission
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
  @RequestMapping(value = VarahamihirConstants.V1_REFRESH_TOKEN_ENDPOINT,
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @RefreshRolePermission
  public Mono<VarahamihirAuthResponse> refresh(ServerWebExchange exchange,
                                               Authentication authentication) {
    return refreshTokenRequest(exchange, authentication).flatMap(tokenRequest -> {

      return refreshTokenRequest(exchange, authentication);
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
  private Mono<VarahamihirAuthResponse> refreshTokenRequest(ServerWebExchange exchange, Authentication authentication) {
    VarahamihirOAuth2Principal principal = ((VarahamihirOAuth2Principal) authentication.getPrincipal());
    switch (principal.getSubjectType()) {
      case CLIENT: {

        Mono<VarahamihirClientDetails> monoClientDetails
                = clientService.findByClientId(principal.getUsername());
        return monoClientDetails.flatMap(clientDetails -> clientCredential(clientDetails, TokenRequestWithoutPassword.builder()
                .audience(String.join(",", principal.getAudience()))
                .clientId(principal.getUsername())
                .grantType(principal.getGrantType())
                .username(principal.getUsername())
                .scope(principal.getScope())
                .build()));
      }
      default: {

        return Mono.subscriberContext()
                .flatMap(tenantDiscriminatorContext -> {
                  Tenant tenant
                          = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
                  Mono<User> userMono = userService.findByUsername(principal.getUsername());
                  return userMono.flatMap(user -> password(tenant, user, TokenRequestWithoutPassword.builder()
                          .audience(String.join(",", principal.getAudience()))
                          .clientId(principal.getUsername())
                          .grantType(principal.getGrantType())
                          .username(principal.getUsername())
                          .scope(principal.getScope())
                          .build())).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("User %s not found.", principal.getUsername()))));
                });
      }
    }
  }
  private Mono<VarahamihirAuthResponse> clientCredential(TokenRequest tokenRequest) {

    Mono<VarahamihirClientDetails> monoClientDetails
            = clientService.findByClientId(tokenRequest.getClientId());
    return monoClientDetails.flatMap(clientDetails -> {

      if (passwordEncoder.matches(tokenRequest.getClientSecret(), clientDetails.getClientSecret())
              || tokenRequest.getClientSecret().equals(clientDetails.getClientSecret())) {
        return clientCredential(clientDetails, TokenRequestWithoutPassword.builder()
                .audience(tokenRequest.getAudience())
                .clientId(tokenRequest.getClientId())
                .grantType(tokenRequest.getGrantType())
                .username(tokenRequest.getUsername())
                .build());
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
        return passwordWithMatch(tokenRequest);
      }
      else {

        return Mono.error(new UnauthorizedException(String.format("Password mismatch for client %s", clientDetails.getClientId())));
      }
    }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("Password mismatch for client %s", tokenRequest.getClientId()))));
  }
  private Mono<VarahamihirAuthResponse> clientCredential(VarahamihirClientDetails clientDetails,
                                                         TokenRequestWithoutPassword tokenRequest) {

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
                                AbstractTokenRequest.GrantType.client_credentials,
                                tokenRequest.getAudience(),
                                tokenRequest.getScope()))
                        .expiry(clientDetails.getAccessTokenValidity())
                        .refreshToken(varahamihirJwtUtil.generateToken(tenant,
                                clientDetails,
                                VarahamihirTokenType.REFRESH_TOKEN,
                                AbstractTokenRequest.GrantType.client_credentials,
                                tokenRequest.getAudience(),
                                tokenRequest.getScope()))
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
  private Mono<VarahamihirAuthResponse> password(Tenant tenant,
                                                 User user,
                                                 TokenRequestWithoutPassword tokenRequest) {

    VarahamihirAuthResponse tokenResponse = null;
    try {

      String scope = StringUtils.join(tokenRequest.getScope(), ",");
      tokenResponse = VarahamihirAuthResponse.builder()
              .auth_token(varahamihirJwtUtil.generateToken(tenant,
                      user,
                      VarahamihirTokenType.ACCESS_TOKEN,
                      AbstractTokenRequest.GrantType.password,
                      tokenRequest.getAudience(),
                      scope))
              .expiry(tenant.getExpiry())
              .refreshToken(varahamihirJwtUtil.generateToken(tenant,
                      user,
                      VarahamihirTokenType.REFRESH_TOKEN,
                      AbstractTokenRequest.GrantType.password,
                      tokenRequest.getAudience(),
                      scope))
              .refreshTokenExpiry(tenant.getRefreshExpiry())
              .tokenType(TokenResponse.TokenType.Bearer.name())
              .scope(scope)
              .build();
    } catch (JOSEException e) {
      return Mono.error(e);
    }
    return Mono.just(tokenResponse);
  }
  private Mono<VarahamihirAuthResponse> passwordWithMatch(TokenRequest tokenRequest) {

    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              Mono<User> userMono = userService.findByUsername(tokenRequest.getUsername());
              return userMono.flatMap(user -> {
                if (passwordEncoder.matches(tokenRequest.getPassword(), user.getPassword())) {

                  return password(tenant, user, TokenRequestWithoutPassword.builder()
                          .username(tokenRequest.getUsername())
                          .grantType(tokenRequest.getGrantType())
                          .clientId(tokenRequest.getClientId())
                          .audience(tokenRequest.getAudience())
                          .build());
                }
                else {
                  return Mono.error(new UnauthorizedException(String.format("Invalid password for user %s.", tokenRequest.getUsername())));
                }
              }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("User %s is not found.", tokenRequest.getUsername()))));
            })
            .switchIfEmpty(Mono.error(new UnauthorizedException("No tenant in context.")));
  }
}
