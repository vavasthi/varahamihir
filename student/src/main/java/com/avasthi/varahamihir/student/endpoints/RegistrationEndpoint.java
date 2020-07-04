package com.avasthi.varahamihir.student.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.student.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping(VarahamihirConstants.V1_REGISTRATION_ENDPOINT )
public class RegistrationEndpoint {

  @Autowired
  private StudentService studentService;
  @RequestMapping(value = "/student", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<StudentPojo> createStudent(@PathVariable(value="tenant") String tenant,
                                         @RequestBody UserPojo userPojo) {
    return studentService.createStudent(tenant, userPojo);
  }
}
