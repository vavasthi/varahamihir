package com.avasthi.varahamihir.client.filters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.File;


@Component
@Log4j2
public class TenantHeaderFilter extends VarahamihirAbstractFilter implements WebFilter {
  private static final String defaultClient = "supersecretclient";
  private static final String defaultSecret = "supersecretclient123";

  @Value("${tutorial.default.tenant.discriminator:default}")
  private String defaultDiscriminator = "default";

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
    logHeaders(serverWebExchange, webFilterChain);
    String requestURI = serverWebExchange.getRequest().getPath().toString();
    String[] dirs = requestURI.split(File.separator);
    String tenantDiscriminatorInPath = dirs[1];
    String tenantDiscriminator = serverWebExchange.getRequest().getHeaders().getFirst(VarahamihirConstants.TENANT_HEADER);
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
  private void logHeaders(ServerWebExchange exchange, WebFilterChain chain) {

    chain.filter(exchange).then(Mono.fromRunnable(()->{
      ServerHttpResponse response = exchange.getResponse();
      exchange.getRequest().getHeaders().forEach((k,v)->{
        log.info(String.format("Request headers : %s: %s", k, v));
      });
      HttpHeaders headers = response.getHeaders();
      headers.forEach((k,v)->{
        log.info(String.format("headers : %s: %s", k, v));
        System.out.println(k + " : " + v);
      });
    }));
  };
}
