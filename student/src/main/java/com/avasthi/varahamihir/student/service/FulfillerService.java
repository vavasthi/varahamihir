package com.avasthi.varahamihir.student.service;

import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.AccountType;
import com.avasthi.varahamihir.common.pojos.H2ORole;
import com.avasthi.varahamihir.common.utils.SanjnanMessages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class FulfillerService {
  
  @Autowired
  private AccountRepository accountRepository;

  
  Logger logger = Logger.getLogger(FulfillerService.class);

  public Account createFulfiller(Account account) {
    if (!accountRepository.findAccountByEmail(account.getEmail()).isPresent()) {
      throw new EntityNotFoundException(String.format(SanjnanMessages.ACCOUNT_NOT_FOUND, account.getEmail()));
    }
    if (account.getH2ORoles() == null) {
      account.setH2ORoles(new HashSet<>());
    }
    account.setAccountType(AccountType.FULFILLER);
    account.getH2ORoles().add(new H2ORole(Role.FULFILLER.name()));
    account.setLocked(true);
    account = accountRepository.save(account);
    account.setPassword(null);
    return account;
  }
}
