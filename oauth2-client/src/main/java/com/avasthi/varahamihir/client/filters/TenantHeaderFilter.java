package com.avasthi.varahamihir.client.filters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.filters.VarahamihirAbstractFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.File;


@Component
//@Order(VarahamihirConstants.TENANT_HEADER_PRECEDENCE)
public class TenantHeaderFilter extends VarahamihirAbstractFilter implements WebFilter {
  public static final String TENANT_HEADER = "X-tenant";
  private static final String defaultClient = "supersecretclient";
  private static final String defaultSecret = "supersecretclient123";

  @Value("${tutorial.default.tenant.discriminator:default}")
  private String defaultDiscriminator = "default";

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
    String requestURI = serverWebExchange.getRequest().getPath().toString();
    String[] dirs = requestURI.split(File.separator);
    String tenantDiscriminatorInPath = dirs[1];
    String tenantDiscriminator = serverWebExchange.getRequest().getHeaders().getFirst(TENANT_HEADER);
    if (requestURI.equals(VarahamihirConstants.HEALTH_ENDPOINT) || tenantDiscriminatorInPath.equals("actuator")) {

      tenantDiscriminator = defaultDiscriminator;
      tenantDiscriminatorInPath = defaultDiscriminator;
    }
    if (tenantDiscriminatorInPath.equals(tenantDiscriminator)) {

      final String tenantDiscriminatorInContext = tenantDiscriminator;
      return webFilterChain
              .filter(serverWebExchange)
              .subscriberContext(context -> context.put(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT, tenantDiscriminatorInContext));
    }
    else {
      return unauthorizedException(serverWebExchange,
              String.format("The header X-tenant should be same as the tenant discriminator in URL. Currently are %s and %s ", tenantDiscriminator, tenantDiscriminatorInPath));
    }
  }
}