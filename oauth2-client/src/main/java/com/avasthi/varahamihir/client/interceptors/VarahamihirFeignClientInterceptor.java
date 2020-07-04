package com.avasthi.varahamihir.client.interceptors;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@Component
public class VarahamihirFeignClientInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate requestTemplate) {

    String tenantHeader = VarahamihirConstants.DEFAULT_TENANT;
    Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              String tenantDiscriminator
                      = tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
              requestTemplate.header(VarahamihirConstants.TENANT_HEADER, tenantDiscriminator);
              return Mono.empty();
            }).switchIfEmpty(insertDefaultTenantHeader(requestTemplate));
    Mono.subscriberContext()
            .flatMap(authHeaderContext -> {
              String authHeader = authHeaderContext.<String>get(VarahamihirConstants.AUTHORIZATION_HEADER_IN_CONTEXT);
              requestTemplate.header("Authorization: ", authHeader);
              return Mono.empty();
            });
  }

  private Mono<?> insertDefaultTenantHeader(RequestTemplate requestTemplate) {
    requestTemplate.header(VarahamihirConstants.TENANT_HEADER, VarahamihirConstants.DEFAULT_TENANT);
    return Mono.empty();
  }
}
