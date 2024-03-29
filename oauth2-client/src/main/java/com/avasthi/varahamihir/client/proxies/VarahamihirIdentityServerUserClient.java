package com.avasthi.varahamihir.client.proxies;

import com.avasthi.varahamihir.client.configs.VarahamihirFeignClientConfig;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "identity-server-87prb",
        configuration = VarahamihirFeignClientConfig.class)
public interface VarahamihirIdentityServerUserClient {

  @RequestMapping(method = RequestMethod.GET,
          value = VarahamihirConstants.V1_USER_ENDPOINT + "/{userId}",
          produces = MediaType.APPLICATION_JSON_VALUE)
  UserPojo getUser(@RequestHeader(value = VarahamihirConstants.AUTHORIZATION_HEADER_NAME, required = true) String authorizationHeader,
                         @RequestHeader(value = VarahamihirConstants.TENANT_HEADER, required = true) String tenantheader,
                         @PathVariable("tenant") String tenant,
                         @PathVariable("userId") String userId);
  @RequestMapping(method = RequestMethod.POST,
          value = VarahamihirConstants.V1_REGISTRATION_ENDPOINT + "/user",
          produces = MediaType.APPLICATION_JSON_VALUE)
  UserPojo createUser(@RequestHeader(value = VarahamihirConstants.TENANT_HEADER, required = true) String tenantheader,
                      @PathVariable("tenant") String tenant,
                      @RequestBody UserPojo userPojo);
}
