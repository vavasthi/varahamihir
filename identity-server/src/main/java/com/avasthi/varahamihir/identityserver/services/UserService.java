package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exception.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.AuthorizationHeaderValues;
import com.avasthi.varahamihir.common.pojos.VarahamihirAuthRequest;
import com.avasthi.varahamihir.common.pojos.VarahamihirAuthResponse;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.enums.VarahamihirTokenType;
import com.avasthi.varahamihir.identityserver.pojo.ClientDetailsFromToken;
import com.avasthi.varahamihir.identityserver.repositories.TenantRepository;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirTokenEncoder;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TenantRepository tenantRepository;

  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;

  @Autowired
  @Qualifier(value = "tokenEncoder")
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ClientService clientService;

  public Flux<User> findAll() {
    return Flux.fromIterable(userRepository.findAll());
  }
  public Mono<User> findByUsername(String username) {
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              return Mono.just(userRepository.findUserByTenantAndUsername(tenant.getId(),
                      username).get());
            }).switchIfEmpty(Mono.empty());
  }
  public Mono<User> save(User user) {
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              user.setTenantId(tenant.getId());
              return Mono.just(userRepository.save(user));
            }).switchIfEmpty(Mono.empty());
  }

  public void delete(UUID id) {
    userRepository.deleteById(id);
  }

  public Mono<VarahamihirAuthResponse> getLoginResponse(VarahamihirAuthRequest ar) throws ParseException, JOSEException, BadJOSEException {

    final AuthorizationHeaderValues ahv
            = Mono.subscriberContext()
            .map(ahvContext -> ahvContext.<AuthorizationHeaderValues>get(VarahamihirConstants.AUTHORIZATION_HEADER_IN_CONTEXT)).block();
    ClientDetailsFromToken cdft = getClientDetailsFromToken(ahv);
    Tenant tenant
            = Mono.subscriberContext()
            .map(tenantDiscriminatorContext -> tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT)).block();
    return findByUsername(ar.getUsername()).flatMap((userDetails) -> {

      if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {

        try {
          VarahamihirAuthResponse response
                  = VarahamihirAuthResponse.builder()
                  .jti(UUID.randomUUID())
                  .auth_token(varahamihirJwtUtil.generateToken(tenant, userDetails, VarahamihirTokenType.ACCESS_TOKEN, ar.getAudience()))
                  .tokenType("Bearer")
                  .refreshToken(varahamihirJwtUtil.generateToken(tenant, userDetails, VarahamihirTokenType.REFRESH_TOKEN, ar.getAudience()))
                  .scope(cdft.getScope().stream().collect(Collectors.joining(",")))
                  .expiry(cdft.getAccessTokenExpiry())
                  .refreshTokenExpiry(cdft.getRefreshTokenExpiry())
                  .build();
          return Mono.just(response);
        } catch (JOSEException e) {

        }
      } else {
        return Mono.error(new UnauthorizedException(String.format("Password mismatch")));
      }
      return Mono.empty();
    }).switchIfEmpty(Mono.empty());
  }
  private ClientDetailsFromToken getClientDetailsFromToken(AuthorizationHeaderValues ahv)
          throws ParseException, JOSEException, BadJOSEException {

    AuthorizationHeaderValues authorizationHeaderValues
            = Mono.subscriberContext()
            .map(ahvContext -> ahvContext.<AuthorizationHeaderValues>get(VarahamihirConstants.AUTHORIZATION_HEADER_IN_CONTEXT))
            .block();
    String clientId = null;
    Set<String> scope = null;
    List<String> audience = null;
    VarahamihirClientDetails clientDetails
            = clientService.findByClientId(ahv.getClientId()).block();
    scope = clientDetails.getScope();
    int expiry = clientDetails.getAccessTokenValidity();
    int refreshExpiry = clientDetails.getRefreshTokenValidity();
    switch(authorizationHeaderValues.getAuthType()) {
      case Bearer:
        JWTClaimsSet claimsSet
                = varahamihirJwtUtil.getAllClaimsFromToken(authorizationHeaderValues.getAuthToken());
        clientId = claimsSet.getSubject();
        audience = claimsSet.getAudience();
      case Basic:
        clientId = authorizationHeaderValues.getClientId();
        audience = new ArrayList<>();
    }
    return ClientDetailsFromToken.builder()
            .audience(audience.stream().collect(Collectors.toSet()))
            .clientId(clientId)
            .scope(scope)
            .build();
  }
}
