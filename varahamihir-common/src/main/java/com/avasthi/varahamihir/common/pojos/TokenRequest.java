package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequest {

  public enum GrantType {
    client_credentials,
    password
  }
  @JsonProperty("grant_type")
  private GrantType grantType;
  @JsonProperty("client_id")
  private String clientId;
  @JsonProperty("client_secret")
  private String clientSecret;
  @JsonProperty("username")
  private String username;
  @JsonProperty("password")
  private String password;
  @JsonProperty("audience")
  private String audience;
}
