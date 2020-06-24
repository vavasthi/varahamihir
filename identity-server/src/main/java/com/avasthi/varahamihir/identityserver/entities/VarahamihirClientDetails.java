package com.avasthi.varahamihir.identityserver.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "clients",
        uniqueConstraints =
                {
                        @UniqueConstraint(name = "uq_clientId", columnNames = {"client_id"})
                })
@EntityListeners(AuditingEntityListener.class)
public class VarahamihirClientDetails implements ClientDetails {

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public Set<String> getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(Set<String> resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public Set<String> getScope() {
    return scope;
  }

  public void setScope(Set<String> scope) {
    this.scope = scope;
  }

  public Set<String> getAuthorizedGrantTypes() {
    return authorizedGrantTypes;
  }

  public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  public Set<String> getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  public void setWebServerRedirectUri(Set<String> webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  public void setAuthorities(Set<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public int getAccessTokenValidity() {
    return accessTokenValidity;
  }

  public void setAccessTokenValidity(int accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  public int getRefreshTokenValidity() {
    return refreshTokenValidity;
  }

  public void setRefreshTokenValidity(int refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  public void setAdditionalInformation(Map<String, String> addlInfo) {
    this.addlInfo = addlInfo.entrySet().stream().map(e -> new VarahamihirClientDetailsAdditionalInfo(e.getKey(), e.getValue())).collect(Collectors.toSet());
  }

  public boolean isAutoApprove() {
    return autoApprove;
  }

  public void setAutoApprove(boolean autoApprove) {
    this.autoApprove = autoApprove;
  }

  @Override
  public boolean isSecretRequired() {
    return clientSecret != null;
  }

  @Override
  public boolean isScoped() {
    return scope != null && !scope.isEmpty();
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return webServerRedirectUri;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return authorities.stream().collect(Collectors.toSet());
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return accessTokenValidity;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return refreshTokenValidity;
  }

  @Override
  public boolean isAutoApprove(String s) {
    return autoApprove;
  }

  public Map<String, Object> getAdditionalInformation() {
    Map<String, Object> returnMap = new HashMap<>();
    addlInfo.forEach((k) -> returnMap.put(k.getK(), k.getV()));
    return returnMap;
  }

  @Id
  @Column(name = "client_id", length = 64)
  private String clientId;
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
}
