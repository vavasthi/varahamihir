package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.TokenRequest;
import com.avasthi.varahamihir.common.pojos.TokenResponse;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.enums.VarahamihirTokenType;
import com.avasthi.varahamihir.identityserver.services.ClientService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TokenEndpoint {
  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;

  @Autowired
  @Qualifier(value = "tokenEncoder")
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ClientService clientService;

  @RequestMapping(value = VarahamihirConstants.V1_TOKEN_ENDPOINT,
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public Mono<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
    switch(tokenRequest.getGrantType()) {
      case client_credentials:
        return clientCredential(tokenRequest);
    }
    return Mono.empty();
  }
  private Mono<TokenResponse> clientCredential(TokenRequest tokenRequest) {

    Mono<VarahamihirClientDetails> monoClientDetails
            = clientService.findByClientId(tokenRequest.getClientId());
    return monoClientDetails.flatMap(clientDetails -> {

      if (passwordEncoder.matches(tokenRequest.getClientSecret(), clientDetails.getClientSecret())) {
          return Mono.subscriberContext()
                  .flatMap(tenantDiscriminatorContext -> {
                    Tenant tenant
                            = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
                    TokenResponse tokenResponse = null;
                    try {

                      tokenResponse = TokenResponse.builder()
                              .accessToken(varahamihirJwtUtil.generateToken(tenant,
                                      clientDetails.getClientId(),
                                      Role.CLIENT.getGrantedAuthority(),
                                      VarahamihirTokenType.ACCESS_TOKEN,
                                      tokenRequest.getAudience()))
                              .expiresIn(clientDetails.getAccessTokenValidity())
                              .tokenType(TokenResponse.TokenType.Bearer)
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
}
