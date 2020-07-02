package com.avasthi.varahamihir.common.pojos;

import com.avasthi.varahamihir.common.enums.VarahamihirSubjectType;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import lombok.Builder;
import lombok.Data;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

@Builder
@Data
public class VarahamihirTokenPrincipal implements Principal {
  @Override
  public String getName() {
    return authToken;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
  private String credentials;
  private String authToken;
  private Set<String> grantedAuthorities;
}