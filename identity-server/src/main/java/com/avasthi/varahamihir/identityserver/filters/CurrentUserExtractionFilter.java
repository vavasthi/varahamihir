package com.avasthi.varahamihir.identityserver.filters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

//@Component
@Order(VarahamihirConstants.USER_PRECEDENCE)
public class CurrentUserExtractionFilter extends VarahamihirAbstractFilter implements WebFilter {
  public static final String TENANT_HEADER = "X-tenant";


  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {

    ApplicationContext applicationContext = serverWebExchange.getApplicationContext();
    UserService userService = applicationContext.getBean(UserService.class);
    return ReactiveSecurityContextHolder.getContext().flatMap(securityContext -> {

      Object principal = securityContext.getAuthentication().getPrincipal();
      String username = null;
      if (principal instanceof org.springframework.security.core.userdetails.User) {
        username = ((org.springframework.security.core.userdetails.User)principal).getUsername();
      }
      else if (principal instanceof String) {
        username = (String)principal;
      }
      Mono<User> monoUser = userService.findByUsername(username);
      return monoUser.map(u -> webFilterChain
                .filter(serverWebExchange)
                .subscriberContext(context -> context.put(VarahamihirConstants.USER_IN_CONTEXT, u))
      ).switchIfEmpty(unauthorizedExceptionTenantDoesntExist());
    });
  };
}