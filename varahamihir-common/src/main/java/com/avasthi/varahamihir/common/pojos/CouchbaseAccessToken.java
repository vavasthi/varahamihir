package com.avasthi.varahamihir.common.pojos;

import com.avasthi.varahamihir.common.converters.SerializableObjectConverter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Document
public class CouchbaseAccessToken {

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public OAuth2AccessToken getToken() {
    return token;
  }

  public void setToken(OAuth2AccessToken token) {
    this.token = token;
  }

  public String getAuthenticationId() {
    return authenticationId;
  }

  public void setAuthenticationId(String authenticationId) {
    this.authenticationId = authenticationId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public void setAuthentication(String authentication) {
    this.authentication = authentication;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public OAuth2Authentication getAuthentication() {
    return SerializableObjectConverter.deserialize(authentication);
  }

  public void setAuthentication(OAuth2Authentication authentication) {
    this.authentication = SerializableObjectConverter.serialize(authentication);
  }

  @Id
  private String id;
  private String tokenId;
  private OAuth2AccessToken token;
  private String authenticationId;
  private String username;
  private String clientId;
  private String authentication;
  private String refreshToken;
}