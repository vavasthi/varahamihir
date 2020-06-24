package com.avasthi.varahamihir.identityserver.utils;


import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;

public class VarahamihirRequestContext {

  public static final ThreadLocal<Tenant> currentTenant = new ThreadLocal<>();
  public static final ThreadLocal<User> currentUser = new ThreadLocal<>();
  public  static final ThreadLocal<String> currentTenantDiscriminator = new ThreadLocal<>();
}
