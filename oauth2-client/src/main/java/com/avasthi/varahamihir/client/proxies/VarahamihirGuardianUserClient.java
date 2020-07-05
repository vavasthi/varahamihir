package com.avasthi.varahamihir.client.proxies;

import com.avasthi.varahamihir.client.configs.VarahamihirFeignClientConfig;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.GuardianPojo;
import com.avasthi.varahamihir.common.pojos.RegistrationPojo;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "guardian-service",
        configuration = VarahamihirFeignClientConfig.class)
public interface VarahamihirGuardianUserClient {

  @RequestMapping(method = RequestMethod.GET,
          value = VarahamihirConstants.V1_GUARDIAN_ENDPOINT + "/{guardianId}",
          produces = MediaType.APPLICATION_JSON_VALUE)
  GuardianPojo getGuardian(@RequestHeader(value = VarahamihirConstants.AUTHORIZATION_HEADER_NAME, required = true) String authorizationHeader,
                           @RequestHeader(value = VarahamihirConstants.TENANT_HEADER, required = true) String tenantheader,
                           @PathVariable("tenant") String tenant,
                           @PathVariable("guardianId") String guardianId);
}
