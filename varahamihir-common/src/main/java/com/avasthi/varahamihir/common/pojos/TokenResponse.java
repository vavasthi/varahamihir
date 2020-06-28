package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

  public enum TokenType {
    Bearer
  }
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("token_type")
  private TokenType tokenType;
  @JsonProperty("expires_in")
  private int expiresIn;
}
