package com.avasthi.varahamihir.guardian.service;

import com.avasthi.varahamihir.client.proxies.VarahamihirIdentityServerUserClient;
import com.avasthi.varahamihir.client.proxies.VarahamihirStudentUserClient;
import com.avasthi.varahamihir.client.services.VarahamihirAbstractFeignClientService;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exceptions.*;
import com.avasthi.varahamihir.common.pojos.GuardianPojo;
import com.avasthi.varahamihir.common.pojos.RegistrationPojo;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.guardian.entities.Guardian;
import com.avasthi.varahamihir.guardian.mappers.GuardianPojoMapper;
import com.avasthi.varahamihir.guardian.repositories.GuardianRepository;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Log4j2
@Service
public class GuardianService extends VarahamihirAbstractFeignClientService {

  @Autowired
  private VarahamihirIdentityServerUserClient identityServerUserClient;
  @Autowired
  private VarahamihirStudentUserClient studentUserClient;
  @Autowired
  private GuardianRepository guardianRepository;
  @Autowired
  private GuardianPojoMapper guardianPojoMapper;

  public Mono<GuardianPojo> getGuardian(String authorizationHeader,
                                        String username) {

    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              UserPojo user = null;
              try {

                user = identityServerUserClient.getUser(authorizationHeader, tenantDiscriminator, tenantDiscriminator, username);
              }
              catch(FeignException fex) {

                return Mono.error(convertFeignException(fex, username, tenantDiscriminator));
              }
              Optional<Guardian> optionalGuardian = guardianRepository.findById(user.getId());
              if (optionalGuardian.isPresent()) {
                return Mono.just(guardianPojoMapper.convert(optionalGuardian.get()));
              } else {
                return Mono.error(new UnauthorizedException(String.format("Student doesn't exist with username", username)));
              }

            }).switchIfEmpty(Mono.error(new EntityNotFoundException(String.format("User %s is not registered.", username))))
            .doOnError(e -> log.error("Identity Server replica not available."+ e.toString(), e))
            .onErrorResume((e -> Mono.error(raiseAppropriateException((Exception) e))));
  }
  public Mono<GuardianPojo> createGuardian(String tenant,
                                          RegistrationPojo registrationPojo) {

    log.error("Calling create guardian for user :" + registrationPojo.getUsername());
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              try {

                UserPojo userPojo
                        = UserPojo.builder()
                        .username(registrationPojo.getUsername())
                        .email(registrationPojo.getPassword())
                        .password(registrationPojo.getPassword())
                        .build();
                userPojo.setGrantedAuthorities(new HashSet<>(Arrays.asList(Role.GUARDIAN.name(), Role.USER.name())));
                UserPojo createdUserPojo
                        = identityServerUserClient.createUser(tenantDiscriminator, tenantDiscriminator, userPojo);
                Guardian guardian = Guardian.builder()
                        .tenantId(createdUserPojo.getTenantId())
                        .userId(createdUserPojo.getId())
                        .build();
                guardian = guardianRepository.save(guardian);
                GuardianPojo guardianPojo = guardianPojoMapper.convert(guardian);
                guardianPojo.setUser(createdUserPojo);
                return Mono.just(guardianPojo);
              }
              catch(FeignException fex) {

                return Mono.error(convertFeignException(fex, registrationPojo.getUsername(), tenantDiscriminator));
              }
            }).switchIfEmpty(Mono.error(new EntityNotFoundException("Student is not registered.")))
            .doOnError(e -> log.error("Replica not available."+ e.toString(), e))
            .onErrorResume((e -> Mono.error(raiseAppropriateException((Exception) e))));
  }
  public Mono<StudentPojo> createStudentForGuardian(String authorizationHeader,
                                                    String tenant,
                                                    String guardianName,
                                                    RegistrationPojo registrationPojo) {

    log.error("Calling create Student for user :" + registrationPojo.getUsername() + " and guardian " + guardianName);
    registrationPojo.setGuardianName(guardianName);
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              try {

                Set<String> grantedAuthorities
                        = new HashSet<>(Arrays.asList(Role.STUDENT.name(), Role.USER.name()));
                UserPojo guardianUser
                        = identityServerUserClient.getUser(authorizationHeader, tenant, tenant, guardianName);
                Optional<Guardian> optionalGuardian
                        = guardianRepository.findById(guardianUser.getId());

                if (optionalGuardian.isPresent()) {

                  Guardian guardian = optionalGuardian.get();
                  registrationPojo.setGuardianId(guardian.getUserId());

                  StudentPojo studentPojo
                          = studentUserClient.createStudent(authorizationHeader, tenantDiscriminator, tenant, registrationPojo);
                  if (guardian.getChildren() == null) {
                    guardian.setChildren(new HashSet<>(Arrays.asList(studentPojo.getUserId())));
                  }
                  else {
                    guardian.getChildren().add(studentPojo.getUserId());
                  }
                  guardianRepository.save(guardian);
                  log.error(String.format("Created user for student, id=%s", studentPojo.getUserId()));
                  return Mono.just(studentPojo);
                }
                else {
                  return Mono.error(new EntityNotFoundException(String.format("Guardian %s doesn't exist", registrationPojo.getGuardianName())));
                }
              }
              catch(FeignException fex) {

                return Mono.error(convertFeignException(fex, registrationPojo.getUsername(), tenantDiscriminator));
              }
            }).switchIfEmpty(Mono.error(new EntityNotFoundException("Student is not registered.")))
            .doOnError(e -> log.error("Eeplica not available."+ e.toString(), e))
            .onErrorResume((e -> Mono.error(raiseAppropriateException((Exception) e))));
  }
}
