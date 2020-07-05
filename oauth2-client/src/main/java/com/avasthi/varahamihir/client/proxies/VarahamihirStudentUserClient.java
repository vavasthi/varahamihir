package com.avasthi.varahamihir.client.proxies;

import com.avasthi.varahamihir.client.configs.VarahamihirFeignClientConfig;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.GuardianPojo;
import com.avasthi.varahamihir.common.pojos.RegistrationPojo;
import com.avasthi.varahamihir.common.pojos.StudentPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "student-service",
        configuration = VarahamihirFeignClientConfig.class)
public interface VarahamihirStudentUserClient {

  @RequestMapping(method = RequestMethod.GET,
          value = VarahamihirConstants.V1_USER_ENDPOINT + "/{studentId}",
          produces = MediaType.APPLICATION_JSON_VALUE)
  StudentPojo getStudent(@RequestHeader(value = VarahamihirConstants.AUTHORIZATION_HEADER_NAME, required = true) String authorizationHeader,
                         @RequestHeader(value = VarahamihirConstants.TENANT_HEADER, required = true) String tenantheader,
                         @PathVariable("tenant") String tenant,
                         @PathVariable("guardianId") String guardianId);
  @RequestMapping(method = RequestMethod.POST,
          value = VarahamihirConstants.V1_STUDENT_ENDPOINT,
          produces = MediaType.APPLICATION_JSON_VALUE)
  StudentPojo createStudent(@RequestHeader(value = VarahamihirConstants.AUTHORIZATION_HEADER_NAME, required = true) String authorizationHeader,
                            @RequestHeader(value = VarahamihirConstants.TENANT_HEADER, required = true) String tenantheader,
                            @PathVariable("tenant") String tenant,
                            @RequestBody RegistrationPojo registrationPojo);
}
