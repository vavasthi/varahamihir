package com.avasthi.varahamihir.student.service;

import com.avasthi.varahamihir.client.common.VarahamihirIdentityServerUserClient;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.student.entities.Student;
import com.avasthi.varahamihir.student.mappers.StudentPojoMapper;
import com.avasthi.varahamihir.student.repositories.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@Service
public class StudentService {

  @Autowired
  private VarahamihirIdentityServerUserClient identityServerUserClient;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private StudentPojoMapper studentPojoMapper;

  public Mono<StudentPojo> getUser(String authorizationHeader,
                                   String username) {

    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              return identityServerUserClient.getUser(authorizationHeader, tenantDiscriminator, tenantDiscriminator, username)
                      .flatMap(user -> {
                        Optional<Student> optionalStudent
                                = studentRepository.findById(user.getId());
                        if (optionalStudent.isPresent()) {
                          return Mono.just(studentPojoMapper.convert(optionalStudent.get()));
                        } else {
                          return Mono.error(new UnauthorizedException(String.format("Student doesn't exist with username", username)));
                        }

                      });
            });
  }
}
