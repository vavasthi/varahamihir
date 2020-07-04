package com.avasthi.varahamihir.student.service;

import com.avasthi.varahamihir.client.proxies.VarahamihirIdentityServerUserClient;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exceptions.*;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.student.entities.Student;
import com.avasthi.varahamihir.student.mappers.StudentPojoMapper;
import com.avasthi.varahamihir.student.repositories.StudentRepository;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Log4j2
@Service
public class StudentService {

  @Autowired
  private VarahamihirIdentityServerUserClient identityServerUserClient;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private StudentPojoMapper studentPojoMapper;

  public Mono<StudentPojo> getStudent(String authorizationHeader,
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
              Optional<Student> optionalStudent = studentRepository.findById(user.getId());
              if (optionalStudent.isPresent()) {
                return Mono.just(studentPojoMapper.convert(optionalStudent.get()));
              } else {
                return Mono.error(new UnauthorizedException(String.format("Student doesn't exist with username", username)));
              }

            }).switchIfEmpty(Mono.error(new EntityNotFoundException(String.format("User %s is not registered.", username))))
            .doOnError(e -> log.error("Identity Server replica not available."+ e.toString(), e))
            .onErrorResume((e -> Mono.error(raiseAppropriateException((Exception) e))));
  }
  public Mono<StudentPojo> createStudent(String tenant,
                                         UserPojo userPojo) {

    log.error("Calling create Student for user :" + userPojo.getUsername());
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              try {

                if (userPojo.getGrantedAuthorities() == null) {
                  userPojo.setGrantedAuthorities(new HashSet<>());
                }
                else {

                  userPojo.getGrantedAuthorities().removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String role) {
                      Set<String> allowedRoles = Role.allowedOnRegister();
                      return !allowedRoles.contains(role);
                    }
                  });
                }
                userPojo.getGrantedAuthorities().add(Role.STUDENT.name());
                UserPojo createdUserPojo
                        = identityServerUserClient.createUser(tenantDiscriminator, tenantDiscriminator, userPojo);
                Student student = Student.builder()
                        .tenantId(createdUserPojo.getTenantId())
                        .userId(createdUserPojo.getId())
                        .build();
                student = studentRepository.save(student);
                StudentPojo studentPojo = studentPojoMapper.convert(student);
                studentPojo.setUser(createdUserPojo);
                return Mono.just(studentPojo);
              }
              catch(FeignException fex) {

                return Mono.error(convertFeignException(fex, userPojo.getUsername(), tenantDiscriminator));
              }
            }).switchIfEmpty(Mono.error(new EntityNotFoundException("Student is not registered.")))
            .doOnError(e -> log.error("Identity Server replica not available."+ e.toString(), e))
            .onErrorResume((e -> Mono.error(raiseAppropriateException((Exception) e))));
  }
  private VarahamihirBaseException raiseAppropriateException(Throwable e) {
    log.error("Exception logged.",e);
    return new MicroservicesPeerUnavailableException("Identity Server replica not available."+ e.toString(), e);
  }
  private VarahamihirBaseException convertFeignException(FeignException fex, String userId, String tenant) {

    HttpStatus status = HttpStatus.resolve(fex.status());
    if (status != null) {

      switch(status) {
        case UNAUTHORIZED:
          return new UnauthorizedException("Authentication failed.", fex);
        case NOT_FOUND:
          return new EntityNotFoundException(String.format("User %s for tenant %s doesn't exist.", userId, tenant));
        case FORBIDDEN:
          return new UnauthorizedException(String.format("This request is forbidden."));
        case GATEWAY_TIMEOUT:
          return new MicroservicesPeerUnavailableException("The server timed out.");
        default:
          return new UnknownException("There is an unknown error in calling server.");
      }
    }
    return new UnknownException("Unknown exception", fex);
  }
}
