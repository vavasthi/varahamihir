package com.avasthi.varahamihir.client.common;

import com.avasthi.varahamihir.client.configs.VarahamihirFeignClientConfig;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

@FeignClient(value = "identity-server-87prb", configuration = VarahamihirFeignClientConfig.class)
public interface VarahamihirIdentityServerUserClient {

  @RequestMapping(method = RequestMethod.GET,
          value = VarahamihirConstants.V1_USER_ENDPOINT + "/{userId}",
          produces = MediaType.APPLICATION_JSON_VALUE)
  Mono<UserPojo> getUser(@RequestHeader(value = VarahamihirConstants.AUTHORIZATION_HEADER_NAME, required = true) String authorizationHeader,
                         @RequestHeader(value = VarahamihirConstants.TENANT_HEADER, required = true) String tenantheader,
                         @PathVariable("tenantId") String tenantDiscriminator,
                         @PathVariable("userId") String userId);
}
