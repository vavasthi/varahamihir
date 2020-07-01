package com.avasthi.varahamihir.common.pojos;

import com.avasthi.varahamihir.common.enums.VarahamihirSubjectType;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenClaims {

  private String subject;
  private String issuer;
  private List<String> audience;
  private List<GrantedAuthority> authorities;
  private VarahamihirSubjectType subjectType;
  private VarahamihirTokenType tokenType;
  private String authToken;
}
