package com.avasthi.varahamihir.student.service;

import com.avasthi.varahamihir.client.proxies.VarahamihirGuardianUserClient;
import com.avasthi.varahamihir.client.proxies.VarahamihirIdentityServerUserClient;
import com.avasthi.varahamihir.client.services.VarahamihirAbstractFeignClientService;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exceptions.*;
import com.avasthi.varahamihir.common.pojos.GuardianPojo;
import com.avasthi.varahamihir.common.pojos.RegistrationPojo;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.student.entities.Student;
import com.avasthi.varahamihir.student.mappers.StudentPojoMapper;
import com.avasthi.varahamihir.student.repositories.StudentRepository;
import feign.FeignException;
import io.fabric8.openshift.api.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
public class StudentService extends VarahamihirAbstractFeignClientService {

  @Autowired
  private VarahamihirIdentityServerUserClient identityServerUserClient;
  @Autowired
  private VarahamihirGuardianUserClient guardianServiceClient;
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
  public Mono<StudentPojo> createStudent(String authorizationHeader,
                                         RegistrationPojo registrationPojo) {

    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              try {

                UserPojo userPojo
                        = UserPojo.builder()
                        .password(registrationPojo.getPassword())
                        .fullname(registrationPojo.getFullname())
                        .email(registrationPojo.getEmail())
                        .username(registrationPojo.getUsername())
                        .grantedAuthorities(new HashSet<>(Arrays.asList(Role.USER.name(), Role.STUDENT.name())))
                        .build();
                userPojo = identityServerUserClient.createUser(tenantDiscriminator, tenantDiscriminator, userPojo);
                registrationPojo.setId(userPojo.getId());
                registrationPojo.setTenantId(userPojo.getTenantId());

                Student student
                        = Student.builder()
                        .userId(registrationPojo.getId())
                        .tenantId(registrationPojo.getTenantId())
                        .guardianId(registrationPojo.getGuardianId())
                        .build();
                student = studentRepository.save(student);
                StudentPojo studentPojo = studentPojoMapper.convert(student);
                studentPojo.setUser(userPojo);
                return Mono.just(studentPojo);
              }
              catch(FeignException fex) {

                return Mono.error(convertFeignException(fex, registrationPojo.getUsername(), tenantDiscriminator));
              }
            }).switchIfEmpty(Mono.error(new EntityNotFoundException(String.format("User %s is not registered.", registrationPojo.getUsername()))))
            .doOnError(e -> log.error("Identity Server replica not available."+ e.toString(), e))
            .onErrorResume((e -> Mono.error(raiseAppropriateException((Exception) e))));
  }
}
