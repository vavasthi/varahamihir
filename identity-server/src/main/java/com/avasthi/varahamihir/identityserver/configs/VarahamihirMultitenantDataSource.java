package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import reactor.core.publisher.Mono;

public class VarahamihirMultitenantDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {

    Mono<String> tenantDiscriminator
            = Mono.subscriberContext()
            .map(tenantDiscriminatorContext -> {
              return tenantDiscriminatorContext.<String>get(VarahamihirConstants.TENANT_DISCRIMINATOR_IN_CONTEXT);
            }).switchIfEmpty(Mono.just("unknown"));
    return tenantDiscriminator.block();
  }
}
