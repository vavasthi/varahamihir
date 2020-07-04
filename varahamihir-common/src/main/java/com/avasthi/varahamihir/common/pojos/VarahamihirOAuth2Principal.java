package com.avasthi.varahamihir.common.pojos;

import com.avasthi.varahamihir.common.enums.VarahamihirSubjectType;
import com.avasthi.varahamihir.common.enums.VarahamihirTokenType;
import lombok.Builder;
import lombok.Data;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class VarahamihirOAuth2Principal implements Principal {
  @Override
  public String getName() {
    return username;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
  private String username;
  private String authToken;
  private VarahamihirTokenType tokenType;
  private VarahamihirSubjectType subjectType;
  private AbstractTokenRequest.GrantType grantType;
  private Set<String> grantedAuthorities;
  private List<String> audience;
  private String scope;
}