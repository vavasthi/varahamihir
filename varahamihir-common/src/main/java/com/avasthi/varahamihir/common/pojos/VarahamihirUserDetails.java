package com.avasthi.varahamihir.common.pojos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class VarahamihirUserDetails implements UserDetails {

  private UUID tenantId;
  private String userName;
  private String password;
  private boolean accountNonLocked;
  private boolean accountMonExpired;
  private boolean credentialsNonExpired;

  private Set<GrantedAuthority> grantedAuthorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Set<GrantedAuthority> getGrantedAuthorities() {
    return grantedAuthorities;
  }

  public void setGrantedAuthorities(Set<GrantedAuthority> grantedAuthorities) {
    this.grantedAuthorities = grantedAuthorities;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
