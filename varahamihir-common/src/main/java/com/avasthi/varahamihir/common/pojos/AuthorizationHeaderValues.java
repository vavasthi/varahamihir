package com.avasthi.varahamihir.common.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationHeaderValues {
  public enum AuthType{
    Bearer,
    Basic
  }
  private AuthType authType;
  private String username;
  private String password;
  private String clientId;
  private String authToken;
}
