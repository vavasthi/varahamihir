package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import com.avasthi.varahamihir.oauth2.couchbase.VarahamihirClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetupService {

  @Autowired
  private AccountService accountService;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private VarahamihirClientDetailsRepository clientDetailsRepository;

  static final String GRANT_TYPE_PASSWORD = "password";
  static final String AUTHORIZATION_CODE = "authorization_code";
  static final String REFRESH_TOKEN = "refresh_token";
  static final String IMPLICIT = "implicit";
  static final String SCOPE_READ = "read";
  static final String SCOPE_WRITE = "write";
  static final String TRUST = "trust";
  public String setup() {
    /**
     * This method checks the account repository and makes sure that one super-admin account is populated. It is required
     * for bootstrap purposes.
     */
/*=    if (accountRepository.count() == 0) {
      Account account = new Account();
      account.setName("superadmin");
      account.setEmail("apps@sanjnan.com");
      account.setPassword("Sanjnan1234#");
      account.setAccountType(AccountType.ADMINISTRATOR);
      account.setVarahamihirRoles(new HashSet<>());
      account.getVarahamihirRoles().add(new VarahamihirRole(Role.SUPERADMIN.name()));
      account.getVarahamihirRoles().add(new VarahamihirRole(Role.USER.name()));
      accountService.createAccount(account);
    }
    if (clientDetailsRepository.count() == 0) {
      VarahamihirClientDetails client = new VarahamihirClientDetails();
      client.setId(UUID.randomUUID().toString());

      client.setResourceIds(new HashSet<>(Arrays.asList("resource_id")) );
      client.setClientId("android-client");
      client.setClientSecret(new SCryptPasswordEncoder().encode("android-secret"));
      client.setAuthorizedGrantTypes(new HashSet<>(Arrays.asList(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)));
      client.setScope(new HashSet<>(Arrays.asList(SCOPE_READ, SCOPE_WRITE, TRUST)));
      client.setSecretRequired(true);
      client.setAccessTokenValiditySeconds(50000);
      client.setRefreshTokenValiditySeconds(50000);
      client.setScoped(false);
      client.setSecretRequired(false);

      clientDetailsRepository.save(client);
    }*/

    return "Setup Complete!";
  }

  public String unsetup() {
    return "System initialized!";
  }
}
