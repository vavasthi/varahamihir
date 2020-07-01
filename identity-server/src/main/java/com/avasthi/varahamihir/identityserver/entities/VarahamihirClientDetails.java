package com.avasthi.varahamihir.identityserver.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients",
        uniqueConstraints =
                {
                        @UniqueConstraint(name = "uq_clientId", columnNames = {"client_id"})
                })
@EntityListeners(AuditingEntityListener.class)
public class VarahamihirClientDetails {

  @Id
  @org.hibernate.annotations.Type(type="uuid-char")
  private UUID id;
  @Column(name = "client_id", length = 64)
  private String clientId;
  @Column(name = "tenant_id")
  @org.hibernate.annotations.Type(type="uuid-char")
  private UUID tenantId;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "clients_resource_ids", joinColumns = @JoinColumn(name = "client_id"))
  @Column(name = "resource_ids")
  private Set<String> resourceIds;
  @Column(name = "client_secret")
  private String clientSecret;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "clients_scope", joinColumns = @JoinColumn(name = "client_id"))
  private Set<String> scope;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "clients_authorized_grant_types", joinColumns = @JoinColumn(name = "client_id"))
  @Column(name = "authorized_grant_types")
  private Set<String> authorizedGrantTypes;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "clients_redirect_uri", joinColumns = @JoinColumn(name = "client_id"))
  @Column(name = "web_server_redirect_uri")
  private Set<String> webServerRedirectUri;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "clients_authorities", joinColumns = @JoinColumn(name = "client_id"))
  private Set<GrantedAuthority> authorities;
  @Column(name = "access_token_validity")
  private int accessTokenValidity;
  @Column(name = "refresh_token_validity")
  private int refreshTokenValidity;
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "clients_addl_info", joinColumns = @JoinColumn(name = "client_id"))
  @Column(name = "addl_info")
  private Set<VarahamihirClientDetailsAdditionalInfo> addlInfo;
  @Column(name = "auto_approve")
  private boolean autoApprove;
  private boolean expired = false;
  private boolean locked = false;
  private boolean credentialsLocked = false;
}
