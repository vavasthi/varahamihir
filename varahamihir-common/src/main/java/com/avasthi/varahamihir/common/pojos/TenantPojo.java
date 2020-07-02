package com.avasthi.varahamihir.common.pojos;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

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
