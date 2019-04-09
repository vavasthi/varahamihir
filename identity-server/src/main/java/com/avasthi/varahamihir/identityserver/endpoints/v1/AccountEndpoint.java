/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.identityserver.endpoints.v1;

import com.avasthi.varahamihir.identityserver.services.AccountService;
import com.avasthi.varahamihir.common.constants.SanjnanConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.SanjnanUserDetail;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import com.avasthi.varahamihir.oauth2.services.SanjnanTokenStore;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(SanjnanConstants.V1_ACCOUNT_ENDPOINT)
@Api(value="Traditional account endpoint", description="This endpoint provides Account lifecycle operations")
public class AccountEndpoint extends BaseEndpoint {

  Logger logger = Logger.getLogger(AccountEndpoint.class);

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private AccountService accountService;
  @Autowired
  private SanjnanTokenStore tokenStore;
  @Autowired
  private DefaultTokenServices defaultTokenServices;



  @Transactional(readOnly = true)
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Account> getAccount() throws IOException {

    SanjnanUserDetail userDetail = (SanjnanUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Account account = userDetail.getAccount();
    if (account != null) {

      account.setPassword(null);
    }
    return Optional.of(account);
  }

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{idOrUsername}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostAuthorize(SanjnanConstants.ANNOTATION_ROLE_ADMIN_OR_CURRENT_USER)
  public Optional<Account> getAccount(@PathVariable("idOrUsername") String id_or_username) throws IOException {

    Optional<Account> optionalAccount = accountService.getAccount(id_or_username);
    if (optionalAccount.isPresent()) {
      Account account = optionalAccount.get();
      account.setPassword(null);
      optionalAccount = Optional.of(account);
    }
    return optionalAccount;
  }

  @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account createAccount(@RequestBody @Valid Account account) {
    return accountService.createAccount(account);
  }

  @Transactional
  @PreAuthorize(SanjnanConstants.ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN)
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account updateAccount(@PathVariable("id") UUID id,
                               @RequestBody @Valid Account account) throws InvocationTargetException, IllegalAccessException {
    return accountService.updateAccount(id, account);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(SanjnanConstants.ANNOTATION_ROLE_ADMIN_AND_TENANT_ADMIN)
  public Account deleteAccount(@PathVariable("id") UUID id) throws InvalidParameterSpecException {

    return accountService.deleteAccount(id);
  }
  @Transactional(readOnly = true)
  @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Account> logout() throws IOException {

    OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication();
    OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(oAuth2Authentication);
    Optional<Account> accountOptional = accountRepository.findAccountByEmail(oAuth2Authentication.getName());
    tokenStore.removeAccessToken(accessToken);
    return accountOptional;
  }

}
