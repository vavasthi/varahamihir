package com.avasthi.varahamihir.guardian.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.GuardianPojo;
import com.avasthi.varahamihir.common.pojos.RegistrationPojo;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.guardian.service.GuardianService;
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
  private GuardianService guardianService;
  @RequestMapping(value = "/guardian", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<GuardianPojo> createGuardian(@PathVariable(value="tenant") String tenant,
                                          @RequestBody RegistrationPojo registrationPojo) {
    return guardianService.createGuardian(tenant, registrationPojo);
  }
}
