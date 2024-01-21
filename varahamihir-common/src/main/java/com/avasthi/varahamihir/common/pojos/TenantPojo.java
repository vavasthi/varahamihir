package com.avasthi.varahamihir.common.pojos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenantPojo {

  private String name;
  private String discriminator;
  private String description;
  private boolean defaultValue;
  private int expiry;
  private int refreshExpiry;
}
