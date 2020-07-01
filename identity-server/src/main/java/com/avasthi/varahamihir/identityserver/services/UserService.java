package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.common.exceptions.InvalidTenantDiscriminatorException;
import com.avasthi.varahamihir.common.exceptions.TokenInvalidException;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.AuthorizationHeaderValues;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.common.pojos.VarahamihirAuthRequest;
import com.avasthi.varahamihir.common.pojos.VarahamihirAuthResponse;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import com.avasthi.varahamihir.identityserver.mappers.UserPojoMapper;
import com.avasthi.varahamihir.identityserver.pojo.ClientDetailsFromToken;
import com.avasthi.varahamihir.identityserver.repositories.TenantRepository;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirJWTUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserPojoMapper userPojoMapper;

  @Autowired
  private TenantRepository tenantRepository;

  @Autowired
  private VarahamihirJWTUtil varahamihirJwtUtil;

  @Autowired
  @Qualifier(value = "passwordEncoder")
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ClientService clientService;

  public Flux<User> findAll() {
    return Flux.fromIterable(userRepository.findAll());
  }

  public Mono<User> findByUsername(String username) {
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              Optional<Tenant> optionalTenant
                      = tenantRepository.findTenantsByDiscriminator(tenantDiscriminator);
              if (optionalTenant.isPresent()) {

                Tenant tenant = optionalTenant.get();
                Optional<User> optionalUser
                        = userRepository.findUserByTenantAndUsername(tenant.getId(), username);
                if (optionalUser.isPresent()) {
                  return Mono.just(optionalUser.get());
                }
                return Mono.error(new EntityNotFoundException(String.format("User %s doesn't exist.", username)));
              }
              else {

                return Mono.error(new EntityNotFoundException(String.format("Tenant %s doesn't exist.", tenantDiscriminator)));
              }
            }).switchIfEmpty(Mono.empty());
  }

  public Mono<User> updatePassword(String username, String password) {
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              Optional<User> optionalUser
                      = userRepository.findUserByTenantAndUsername(tenant.getId(), username);
              if (optionalUser.isPresent()) {
                User u = optionalUser.get();
                u.setPassword(passwordEncoder.encode(password));
                userRepository.save(u);
                return Mono.just(optionalUser.get());
              }
              return Mono.error(new EntityNotFoundException(String.format("User %s doesn't exist.", username)));
            }).switchIfEmpty(Mono.empty());
  }

  public void delete(UUID id) {
    userRepository.deleteById(id);
  }

  public Mono<VarahamihirAuthResponse> getLoginResponse(VarahamihirAuthRequest ar)
          throws ParseException, JOSEException, BadJOSEException {

    return Mono.subscriberContext()
            .flatMap(tenantContext -> {

              Tenant tenant = tenantContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);

              return Mono.subscriberContext()
                      .flatMap(ahvContext -> {
                        try {

                          AuthorizationHeaderValues ahv
                                  = ahvContext.<AuthorizationHeaderValues>get(VarahamihirConstants.AUTHORIZATION_HEADER_IN_CONTEXT);
                          return getClientDetailsFromToken(ahv)
                                  .flatMap(cdft -> {

                                    return findByUsername(ar.getUsername()).flatMap((userDetails) -> {

                                      if (passwordEncoder.matches(ar.getPassword(), userDetails.getPassword())) {

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
                                      }
                                      return Mono.error(new UnauthorizedException(String.format("Password mismatch")));
                                    }).switchIfEmpty(Mono.error(new EntityNotFoundException(String.format("User %s doesn't exist", ar.getUsername()))));
                                  }).switchIfEmpty(Mono.error(new EntityNotFoundException(String.format("Client  %s could not be authorized.", ahv.getClientId()))));
                        } catch (ParseException | JOSEException | BadJOSEException e) {
                        }
                        return Mono.error(new UnauthorizedException(String.format("Password mismatch")));
                      });
            });
  }

  private Mono<ClientDetailsFromToken> getClientDetailsFromToken(AuthorizationHeaderValues ahv)
          throws ParseException, JOSEException, BadJOSEException {

    return clientService.findByClientId(ahv.getClientId())
            .flatMap( clientDetails -> {

              Set<String> scope = clientDetails.getScope();
              int expiry = clientDetails.getAccessTokenValidity();
              int refreshExpiry = clientDetails.getRefreshTokenValidity();
              switch(ahv.getAuthType()) {
                case Bearer:
                  try {

                    JWTClaimsSet claimsSet
                            = varahamihirJwtUtil.getAllClaimsFromToken(ahv.getAuthToken());
                    return Mono.just(ClientDetailsFromToken.builder()
                            .audience(claimsSet.getAudience().stream().collect(Collectors.toSet()))
                            .clientId(claimsSet.getSubject())
                            .scope(scope)
                            .accessTokenExpiry(expiry)
                            .refreshTokenExpiry(refreshExpiry)
                            .build());
                  } catch (ParseException|JOSEException|BadJOSEException e) {
                    e.printStackTrace();
                  }
                case Basic:
                  return Mono.just(ClientDetailsFromToken.builder()
                          .audience(new HashSet<String>())
                          .clientId(ahv.getClientId())
                          .scope(scope)
                          .accessTokenExpiry(expiry)
                          .refreshTokenExpiry(refreshExpiry)
                          .build());
              }
              return Mono.error(new TokenInvalidException("Token is invalid."));
            }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("Client %s doesn't exist.", ahv.getClientId()))));
  }

  public Mono<UserPojo> create(UserPojo userPojo) {

    return Mono.subscriberContext()
            .map(tenantContext -> {
              Tenant tenant
                      = tenantContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              User user = userPojoMapper.convert(userPojo);
              user.setPassword(passwordEncoder.encode(userPojo.getPassword()));
              user.setId(UUID.randomUUID());
              user.setTenantId(tenant.getId());
              userRepository.save(user);
              return userPojoMapper.convert(user);
            }).switchIfEmpty(Mono.error(new InvalidTenantDiscriminatorException("Tenant doesn't exist")));
  }
}
