
package com.avasthi.varahamihir.common.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Role {
  USER("user"),
  CLIENT("client", false),
  TESTER("tester"),
  ADMIN("admin", false),
  TENANT_ADMIN("tenant_admin", false),
  STUDENT("student"),
  GUARDIAN("guardian"),
  NEWUSER("newUser", false),
  REFRESH("refresh", false),
  AUTHENTICATE("authenticate", false);

  private final String value;
  private final boolean allowedOnCreate;

  Role(String value) {
    this.value = value;
    this.allowedOnCreate = true;
  }
  Role(String value,  boolean allowedOnCreate) {
    this.value = value;
    this.allowedOnCreate = allowedOnCreate;
  }

  public static Role createFromString(String value) {
    for (Role r : Role.values()) {
      if (r.value.equalsIgnoreCase(value)) {
        return r;
      }
    }
    throw new IllegalArgumentException(value + " is not a valid value");
  }

 // public final String getValue() {
 //   return value;
//  }

  public Set<String> getGrantedAuthority() {
    return new HashSet<String>(Arrays.asList(value));
  }
  public Set<String> getGrantedAuthority(Set<Role> roles) {
    return roles.stream().map(r -> r.value).collect(Collectors.toSet());
  }
  public static Set<String> allowedOnRegister() {
    return  Arrays.stream(values()).filter(new Predicate<Role>() {
      @Override
      public boolean test(Role role) {
        return role.allowedOnCreate;
      }
    }).map(r -> r.name()).collect(Collectors.toSet());
  }
}
