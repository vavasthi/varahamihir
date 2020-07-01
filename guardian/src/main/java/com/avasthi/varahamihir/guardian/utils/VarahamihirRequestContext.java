package com.avasthi.varahamihir.guardian.utils;


import com.avasthi.varahamihir.common.pojos.UserPojo;

public class VarahamihirRequestContext {

  public static final ThreadLocal<UserPojo> currentUser = new ThreadLocal<>();
  public  static final ThreadLocal<String> currentTenantDiscriminator = new ThreadLocal<>();
}
