package com.avasthi.varahamihir.student.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentUserBodyPermission;
import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrGuardianPermission;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.RegistrationPojo;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.common.pojos.VarahamihirTokenPrincipal;
import com.avasthi.varahamihir.student.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Created by vinay on 1/4/16.
 */
@Log4j2
@RestController
@RequestMapping(VarahamihirConstants.V1_STUDENT_ENDPOINT)
@Api(value="Student endpoint")
public class StudentEndpoint extends BaseEndpoint {


  @Autowired
  private StudentService studentService;

  @RequestMapping(value = "/{studentname}",
          method = RequestMethod.GET,
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @AdminTenantAdminOrCurrentUserBodyPermission
  public Mono<StudentPojo> getStudent(@PathVariable("studentname") String username,
                                      Authentication authentication) {

    VarahamihirTokenPrincipal principal
            = (VarahamihirTokenPrincipal)authentication.getPrincipal();
    return studentService.getStudent(String.format("Bearer %s", principal.getAuthToken()), username);
  }
  @RequestMapping(method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @AdminTenantAdminOrGuardianPermission
  public Mono<StudentPojo> createStudent(@RequestBody RegistrationPojo registrationPojo,
                                         Authentication authentication) {

    VarahamihirTokenPrincipal principal
            = (VarahamihirTokenPrincipal)authentication.getPrincipal();
    return studentService.createStudent(String.format("Bearer %s", principal.getAuthToken()), registrationPojo);
  }

}
