
package com.avasthi.varahamihir.common.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
  USER("user"),
  CLIENT("client"),
  TESTER("tester"),
  ADMIN("admin"),
  TENANT_ADMIN("tenant_admin"),
  STUDENT("student"),
  GUARDIAN("guardian"),
  NEWUSER("newUser"),
  REFRESH("refresh"),
  AUTHENTICATE("authenticate");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  public static Role createFromString(String value) {
    for (Role r : Role.values()) {
      if (r.value.equalsIgnoreCase(value)) {
        return r;
      }
    }
    throw new IllegalArgumentException(value + " is not a valid value");
  }

  public final String getValue() {
    return value;
  }

  public Set<String> getGrantedAuthority() {
    return new HashSet<String>(Arrays.asList(value));
  }
  public Set<String> getGrantedAuthority(Set<Role> roles) {
    return roles.stream().map(r -> r.value).collect(Collectors.toSet());
  }
}
