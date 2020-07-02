package com.avasthi.varahamihir.client.common;

import com.avasthi.varahamihir.common.pojos.UserPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

@FeignClient("identityServer")
public interface VarahamihirIdentityServerUserClient {

  @RequestMapping(method = RequestMethod.GET, value = "{tenantId}/users/{userId}")
  Mono<UserPojo> getUser(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                         @PathVariable("tenantId") String tenantDiscriminator,
                         @PathVariable("userId") String userId);
}
