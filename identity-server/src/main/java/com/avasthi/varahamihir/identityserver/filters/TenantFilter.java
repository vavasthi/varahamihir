package com.avasthi.varahamihir.identityserver.filters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.services.TenantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;


//@Component
//@Order(VarahamihirConstants.TENANT_PRECEDENCE)
public class TenantFilter extends VarahamihirAbstractFilter implements WebFilter {
  public static final String TENANT_HEADER = "X-tenant";
  private static final String defaultClient = "supersecretclient";
  private static final String defaultSecret = "supersecretclient123";

  @Value("${tutorial.default.tenant.discriminator:default}")
  private String defaultDiscriminator;

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
    logSecurityContext(LoginWebFilter.class);
    ApplicationContext applicationContext = serverWebExchange.getApplicationContext();
    TenantService tenantService = applicationContext.getBean(TenantService.class);
     return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              Optional<Tenant> optionalTenant = tenantService.retrieveTenantsByDiscriminator(tenantDiscriminator);
              if (optionalTenant.isPresent()) {

                return webFilterChain
                        .filter(serverWebExchange)
                        .subscriberContext(context -> context.put(VarahamihirConstants.TENANT_IN_CONTEXT, optionalTenant.get()));
              }
              else {

                return unauthorizedException(serverWebExchange,
                        String.format("Tenant header is not present"));
              }
            });
  }
}