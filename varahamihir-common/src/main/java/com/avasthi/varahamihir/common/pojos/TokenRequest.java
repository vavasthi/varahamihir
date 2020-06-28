package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
  @JsonProperty("audience")
  private String audience;
}
