package com.avasthi.varahamihir.common.utils;


import com.avasthi.varahamihir.common.entities.Tenant;
import com.avasthi.varahamihir.common.entities.User;

public class VarahamihirRequestContext {

  public static final ThreadLocal<Tenant> currentTenant = new ThreadLocal<>();
  public static final ThreadLocal<User> currentUser = new ThreadLocal<>();
  public  static final ThreadLocal<String> currentTenantDiscriminator = new ThreadLocal<>();
}
