package com.avasthi.varahamihir.common.configs;

import com.avasthi.varahamihir.common.utils.VarahamihirRequestContext;
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
