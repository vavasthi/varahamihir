package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequestWithoutPassword extends AbstractTokenRequest{

  @JsonProperty("grant_type")
  private GrantType grantType;
  @JsonProperty("client_id")
  private String clientId;
  @JsonProperty("username")
  private String username;
  @JsonProperty("audience")
  private String audience;
  @JsonProperty("scope")
  private String scope;
}
