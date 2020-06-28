package com.avasthi.varahamihir.guardian.configs;

import com.avasthi.varahamihir.guardian.utils.VarahamihirRequestContext;
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
