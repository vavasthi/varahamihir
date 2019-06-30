package com.avasthi.varahamihir.oauth2client.security.utils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class OAuthResponse {
  public List<String> getAud() {
    return aud;
  }

  public void setAud(List<String> aud) {
    this.aud = aud;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public List<String> getScope() {
    return scope;
  }

  public void setScope(List<String> scope) {
    this.scope = scope;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public DateTime getExpiry() {
    return expiry;
  }

  public void setExpiry(DateTime expiry) {
    this.expiry = expiry;
  }

  public List<String> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<String> authorities) {
    this.authorities = authorities;
  }

  public String getClient_id() {
    return client_id;
  }

  public void setClient_id(String client_id) {
    this.client_id = client_id;
  }

  List<String> aud = new ArrayList<>();
  String user_name;
  List<String> scope = new ArrayList<>();
  boolean active;
  DateTime expiry;
  List<String> authorities = new ArrayList<>();
  String client_id;
}
