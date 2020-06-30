package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.annotations.AdminTenantAdminOrCurrentUserPermission;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.NotFoundException;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.pojos.TokenRequest;
import com.avasthi.varahamihir.common.pojos.TokenResponse;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.mappers.UserPojoMapper;
import com.avasthi.varahamihir.identityserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class UserEndpoint {

  @Autowired
  private UserService userService;
  @Autowired
  private UserPojoMapper userPojoMapper;
  @RequestMapping(value = VarahamihirConstants.V1_USER_ENDPOINT + "/{username}",
          method = RequestMethod.GET,
          consumes = MediaType.APPLICATION_JSON_VALUE)
//  @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN') or (hasAuthority('USER') and #username == authentication.name)")
  public Mono<UserPojo> getUser(@PathVariable("username") String username) {
    Mono<Authentication> monoAuth = ReactiveSecurityContextHolder.getContext().map(c -> c.getAuthentication());
    monoAuth.flatMap(ma -> {

      Mono<User> userMono = userService.findByUsername(username);
      return userMono.map(u -> userPojoMapper.convert(u))
              .switchIfEmpty(Mono.error(new NotFoundException(String.format("User %s is not found", username))));
    }).switchIfEmpty(Mono.error(new UnauthorizedException("")));
    return Mono.empty();
  }
}
