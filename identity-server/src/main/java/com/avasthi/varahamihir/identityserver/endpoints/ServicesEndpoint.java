package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrAuthenticatedPermission;
import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentUserBodyPermission;
import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentUserPermission;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.NotFoundException;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.mappers.UserPojoMapper;
import com.avasthi.varahamihir.identityserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ServicesEndpoint {

  @Autowired
  private DiscoveryClient discoveryClient;

  @RequestMapping(value = VarahamihirConstants.V1_SERVICES_ENDPOINT)
  @AdminTenantAdminOrAuthenticatedPermission
  public Flux<String> getServices() {
    return Flux.fromIterable(discoveryClient.getServices());
  }
}
