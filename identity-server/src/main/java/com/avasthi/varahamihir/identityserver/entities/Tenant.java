package com.avasthi.varahamihir.identityserver.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "tenants",
        uniqueConstraints =
                {
                        @UniqueConstraint(name = "uq_discriminator", columnNames = {"discriminator"}),
                        @UniqueConstraint(name = "uq_name", columnNames = {"name"})
                })
public class Tenant extends AbstractBaseEntity {

  public Tenant() {

  }
  @Column(name = "name")
  private String name;
  @Column(name = "discriminator")
  private String discriminator;
  @Column(name = "description")
  private String description;
  @Column(name = "default_value")
  private boolean defaultValue;
  @Column(name = "expiry")
  private int expiry;
  @Column(name = "refresh_expiry")
  private int refreshExpiry;

}
