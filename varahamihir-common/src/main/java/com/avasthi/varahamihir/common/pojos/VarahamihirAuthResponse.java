package com.avasthi.varahamihir.common.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VarahamihirAuthResponse {

  private UUID jti;
  private String auth_token;
  private int expiry;
  private String tokenType;
  private String refreshToken;
  private int refreshTokenExpiry;
  private String scope;
  private String password;
}
