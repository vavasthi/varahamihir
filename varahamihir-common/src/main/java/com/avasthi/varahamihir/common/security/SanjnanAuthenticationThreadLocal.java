package com.avasthi.varahamihir.common.security;

import com.avasthi.varahamihir.common.pojos.Account;

public class SanjnanAuthenticationThreadLocal {

  public static final SanjnanAuthenticationThreadLocal INSTANCE = new SanjnanAuthenticationThreadLocal();
  public void initialize(Account account) {
    accountThreadLocal.set(account);
  }
  public void clear() {
    accountThreadLocal.remove();
  }
  public ThreadLocal<Account> getAccountThreadLocal() {
    return accountThreadLocal;
  }

  public void setAccountThreadLocal(ThreadLocal<Account> accountThreadLocal) {
    this.accountThreadLocal = accountThreadLocal;
  }

  private ThreadLocal<Account> accountThreadLocal = new ThreadLocal<>();
}
