package com.avasthi.varahamihir.student.service;

import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.AccountType;
import com.avasthi.varahamihir.common.pojos.VarahamihirRole;
import com.avasthi.varahamihir.common.utils.SanjnanMessages;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Log4j2
@Service
public class StudentService {
  
  @Autowired
  private AccountRepository accountRepository;

  

  public Account createFulfiller(Account account) {
    if (!accountRepository.findAccountByEmail(account.getEmail()).isPresent()) {
      throw new EntityNotFoundException(String.format(SanjnanMessages.ACCOUNT_NOT_FOUND, account.getEmail()));
    }
    if (account.getVarahamihirRoles() == null) {
      account.setVarahamihirRoles(new HashSet<>());
    }
    account.setAccountType(AccountType.STUDENT);
    account.getVarahamihirRoles().add(new VarahamihirRole(Role.STUDENT.name()));
    account.setLocked(true);
    account = accountRepository.save(account);
    account.setPassword(null);
    return account;
  }
}
