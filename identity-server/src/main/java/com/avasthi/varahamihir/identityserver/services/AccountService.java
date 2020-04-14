package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.exception.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.utils.VarahamihirPasswordEncryptionManager;
import com.avasthi.varahamihir.common.utils.ObjectPatcher;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.utils.SanjnanMessages;
import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private SetupService setupService;



  public Optional<Account> getAccount(String id_or_email) {

    try {

      UUID id = UUID.fromString(id_or_email);
      log.info("Retrieving account info for UUID = " + id_or_email);
      Optional<Account> accountOptional = accountRepository.findById(id.toString());
      if (accountOptional.isPresent()) {
        return accountOptional;
      }
    }
    catch(IllegalArgumentException iae) {
      log.info("Retrieving account info for username = " + id_or_email);
      Optional<Account> accountOptional = accountRepository.findAccountByEmail(id_or_email);
      if (accountOptional.isPresent()) {
        return accountOptional;
      }
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.ACCOUNT_NOT_FOUND, id_or_email));
  }

  public Account createAccount(Account account) {


    Optional<Account> existingOptionalAccount = accountRepository.findAccountByEmail(account.getEmail());
    if (existingOptionalAccount.isPresent()) {
      throw new EntityAlreadyExistsException(String.format(SanjnanMessages.ACCOUNT_WITH_EMAIL_ALREADY_EXISTS, account.getEmail()));
    }
    if (account.getId() == null) {
      account.setId(UUID.randomUUID().toString());
    }
    account.setPassword(VarahamihirPasswordEncryptionManager.INSTANCE.encrypt(account.getPassword()));
    accountRepository.save(account);
    account.setPassword(null);
    return account;
  }

  public Account updateAccount(UUID id, Account account) throws InvocationTargetException, IllegalAccessException {

    Optional<Account> accountOptional = accountRepository.findById(id.toString());
    if (accountOptional.isPresent()) {
      Account storedAccount = accountOptional.get();
      ObjectPatcher.diffAndPatch(Account.class, storedAccount, Account.class, account);
      accountRepository.save(storedAccount);
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.ACCOUNT_NOT_FOUND, account.getName()));
  }

  public Account deleteAccount(UUID id) {
    Optional<Account> accountOptional = accountRepository.findById(id.toString());
    if (accountOptional.isPresent()) {
      Account storedAccount = accountOptional.get();
      accountRepository.deleteById(id.toString());
      return storedAccount;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.ACCOUNT_NOT_FOUND, id.toString()));
  }

  public boolean validateCredentials(String username, String password) {
    Optional<Account> optionalAccount = getAccount(username);
    if (optionalAccount.isPresent()) {
      Account account = optionalAccount.get();
      return VarahamihirPasswordEncryptionManager.INSTANCE.matches(password, account.getPassword());
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.ACCOUNT_NOT_FOUND, username));
  }

}
