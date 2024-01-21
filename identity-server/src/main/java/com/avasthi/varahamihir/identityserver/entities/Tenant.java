package com.avasthi.varahamihir.identityserver.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tenants")
@Data
@Builder
@AllArgsConstructor
public class Tenant extends AbstractBaseEntity {


  private String name;
  private String discriminator;
  private String description;
  private boolean defaultValue;
  private int expiry;
  private int refreshExpiry;

}
