package com.avasthi.varahamihir.common.security;

import com.avasthi.varahamihir.common.pojos.Account;

public class VarahamihirAuthenticationThreadLocal {

  public static final VarahamihirAuthenticationThreadLocal INSTANCE = new VarahamihirAuthenticationThreadLocal();
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
