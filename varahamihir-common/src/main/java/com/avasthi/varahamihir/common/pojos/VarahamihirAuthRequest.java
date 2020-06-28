package com.avasthi.varahamihir.common.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VarahamihirAuthRequest {

  private String username;
  private String password;
}
