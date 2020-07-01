package com.avasthi.varahamihir.identityserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetailsFromToken {
  private String clientId;
  private Set<String> scope;
  private Set<String> audience;
  private int accessTokenExpiry;
  private int refreshTokenExpiry;
}
