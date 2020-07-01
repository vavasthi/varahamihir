package com.avasthi.varahamihir.common.pojos;

import com.avasthi.varahamihir.common.enums.VarahamihirResponseType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VarahamihirCodeRequest {

  @JsonProperty("response_type")
  private VarahamihirResponseType responseType;
  @JsonProperty("client_id")
  private String clientId;
  @JsonProperty("redirect_uri")
  private String redirect_uri;
  @JsonProperty("scope")
  private String scope;
  @JsonProperty("state")
  private String state;
}
