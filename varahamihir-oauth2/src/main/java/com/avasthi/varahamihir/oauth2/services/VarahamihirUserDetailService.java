package com.avasthi.varahamihir.oauth2.services;

import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.SanjnanUserDetail;
import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VarahamihirUserDetailService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Optional<Account> accountOptional = accountRepository.findAccountByEmail(name);
    if (accountOptional.isPresent()) {
      Account account = accountOptional.get();

      return new SanjnanUserDetail(account);
    }
    throw new UsernameNotFoundException("Could not find the user " + name);
  }
}