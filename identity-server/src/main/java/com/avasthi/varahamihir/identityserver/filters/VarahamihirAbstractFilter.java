package com.avasthi.varahamihir.identityserver.filters;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Log4j2
public class VarahamihirAbstractFilter {
  protected Mono<Void> unauthorizedException(ServerWebExchange serverWebExchange,
                                             String message) {
    serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return Mono.error(new UnauthorizedException(message));
  }
  protected Mono<Void> unauthorizedException(String tenantDiscriminator,
                                             String message) {
    return Mono.error(new UnauthorizedException(message));
  }
  protected Mono unauthorizedExceptionTenantDoesntExist() {
    return getTenantDiscriminatorFromContext()
            .map( tenantDiscriminator -> Mono.error(new UnauthorizedException(String.format("Tenant %s doesn't exist",
                    tenantDiscriminator))))
    .defaultIfEmpty(Mono.error(new UnauthorizedException("Tenant doesn't exist")));
  }
  protected Mono<String> getTenantDiscriminatorFromContext() {

    return Mono.subscriberContext()
            .map(tenantDiscriminatorContext -> {
              return tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
            });
  }
  protected void logSecurityContext(Class<?> currentUserExtractionFilterClass) {
      Mono.subscriberContext()
            .map(context -> {
              log.error(currentUserExtractionFilterClass.getCanonicalName() + "CONTEXT :"  + ":" + context.get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT));
              log.error(currentUserExtractionFilterClass.getCanonicalName() + "CONTEXT :"  + ":" + context.get(VarahamihirConstants.USER_IN_CONTEXT));
              log.error(currentUserExtractionFilterClass.getCanonicalName() + "CONTEXT :"  + ":" + context.get(VarahamihirConstants.TENANT_IN_CONTEXT));
              return context;
            }).switchIfEmpty( Mono.empty());
  }
}
