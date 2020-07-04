/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.student.endpoints;

import com.avasthi.varahamihir.client.common.VarahamihirIdentityServerUserClient;
import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentUserBodyPermission;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.common.pojos.VarahamihirOAuth2Principal;
import com.avasthi.varahamihir.common.pojos.VarahamihirTokenPrincipal;
import com.avasthi.varahamihir.student.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by vinay on 1/4/16.
 */
@Log4j2
@RestController
@RequestMapping(VarahamihirConstants.V1_STUDENT_ENDPOINT)
@Api(value="Student endpoint", description="This endpoint provides Student lifescycle operations")
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
    return studentService.getUser(String.format("Bearer %s", principal.getAuthToken()), username);
  }

}
