/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.guardian.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentGuardianBodyPermission;
import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentUserBodyPermission;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exceptions.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.*;
import com.avasthi.varahamihir.guardian.entities.Guardian;
import com.avasthi.varahamihir.guardian.service.GuardianService;
import feign.FeignException;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vinay on 1/4/16.
 */
@Log4j2
@RestController
@RequestMapping(VarahamihirConstants.V1_GUARDIAN_ENDPOINT)
@Api(value="Guardian endpoint")
public class GuardianEndpoint extends BaseEndpoint {


  @Autowired
  private GuardianService guardianService;

  @RequestMapping(value = "/{guardianname}",
          method = RequestMethod.GET,
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @AdminTenantAdminOrCurrentUserBodyPermission
  public Mono<GuardianPojo> getGuardian(@PathVariable("guardianname") String username,
                                       Authentication authentication) {

    VarahamihirTokenPrincipal principal
            = (VarahamihirTokenPrincipal)authentication.getPrincipal();
    return guardianService.getGuardian(String.format("Bearer %s", principal.getAuthToken()), username);
  }

  @RequestMapping(value = "{guardianName}/student",
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @AdminTenantAdminOrCurrentGuardianBodyPermission
  public Mono<StudentPojo> addStudentToGuardian(@PathVariable("tenant") String tenant,
                                                @PathVariable("guardianName") String guardianName,
                                                @RequestBody RegistrationPojo registrationPojo,
                                                Authentication authentication) {

    VarahamihirTokenPrincipal principal
            = (VarahamihirTokenPrincipal)authentication.getPrincipal();
    return guardianService.createStudentForGuardian(String.format("Bearer %s", principal.getAuthToken()),
            tenant,
            guardianName,
            registrationPojo);
  }
}
