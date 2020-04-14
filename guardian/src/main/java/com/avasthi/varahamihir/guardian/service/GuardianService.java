package com.avasthi.varahamihir.guardian.service;

import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.AccountType;
import com.avasthi.varahamihir.common.pojos.VarahamihirRole;
import com.avasthi.varahamihir.common.utils.SanjnanMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class GuardianService {
  
  @Autowired
  private AccountRepository accountRepository;

  public Account createGuardian(Account account) {
    if (!accountRepository.findAccountByEmail(account.getEmail()).isPresent()) {
      throw new EntityNotFoundException(String.format(SanjnanMessages.ACCOUNT_NOT_FOUND, account.getEmail()));
    }
    if (account.getVarahamihirRoles() == null) {
      account.setVarahamihirRoles(new HashSet<>());
    }
    account.setAccountType(AccountType.GUARDIAN);
    account.getVarahamihirRoles().add(new VarahamihirRole(Role.GUARDIAN.name()));
    account.setLocked(true);
    account = accountRepository.save(account);
    account.setPassword(null);
    return account;
  }
}
