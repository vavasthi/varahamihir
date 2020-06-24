package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.identityserver.utils.VarahamihirRequestContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class VarahamihirMultitenantDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {

    if (VarahamihirRequestContext.currentTenantDiscriminator.get() != null) {
      return VarahamihirRequestContext.currentTenantDiscriminator.get();
    }
    else {
      return "unknown";
    }
  }
}
