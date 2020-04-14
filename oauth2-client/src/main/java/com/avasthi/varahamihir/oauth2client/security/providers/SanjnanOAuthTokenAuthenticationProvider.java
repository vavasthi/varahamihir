package com.avasthi.varahamihir.oauth2client.security.providers;


import com.avasthi.varahamihir.common.pojos.VarahamihirRole;
import com.avasthi.varahamihir.oauth2client.security.utils.OAuthClient;
import com.avasthi.varahamihir.oauth2client.security.utils.OAuthResponse;
import com.google.gson.Gson;
import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.security.VarahamihirAuthenticationThreadLocal;
import com.avasthi.varahamihir.common.security.token.VarahamihirOAuthTokenPrincipal;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import springfox.documentation.service.OAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class SanjnanOAuthTokenAuthenticationProvider implements AuthenticationProvider {


  private final String requestUrl = "http://localhost:8081/oauth/check_token?token=%s&token_type=bearer";
  private final String clientId = "android-client";
  private final String clientSecret = "android-secret";

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private OAuthClient oAuthClient;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    VarahamihirOAuthTokenPrincipal principal = (VarahamihirOAuthTokenPrincipal)authentication.getPrincipal();
    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
      @Override
      public Request authenticate(Route route, Response response) throws IOException {
        String authorizationHeader = "";
        if (principal.getAuthorizationHeader().isPresent()) {
          authorizationHeader = principal.getAuthorizationHeader().get();
        }
        if (responseCount(response) >= 3) {
          return null;
        }
        return response.request().newBuilder().header("Authorization", authorizationHeader).build();
      }
      private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
          result++;
        }
        return result;
      }
    }).build();
    try {

      OAuthResponse oAuthResponse = oAuthClient.checkToken(principal.getToken().get(), "bearer");
        principal.setName(oAuthResponse.getUser_name());
        List<VarahamihirRole> authorityList = new ArrayList<>();
        if (oAuthResponse.getAuthorities() != null) {

          oAuthResponse.getAuthorities().stream().forEach(e -> authorityList.add(new VarahamihirRole(e)));
        }
        Optional<Account> accountOptional = accountRepository.findAccountByEmail(principal.getName());
        VarahamihirAuthenticationThreadLocal.INSTANCE.initialize(accountOptional.get());
        return new PreAuthenticatedAuthenticationToken(principal, principal.getToken(), authorityList);

      /*Request request = new Request.Builder()
              .url(String.format(requestUrl, principal.getToken().get()))
              .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), ""))
              .build();
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        Gson gson = new Gson();
        OAuthResponse oAuthResponse = gson.fromJson(response.body().string(), OAuthResponse.class);
        principal.setName(oAuthResponse.getUser_name());
        List<VarahamihirRole> authorityList = new ArrayList<>();
        if (oAuthResponse.getAuthorities() != null) {

          oAuthResponse.getAuthorities().stream().forEach(e -> authorityList.add(new VarahamihirRole(e)));
        }
        Optional<Account> accountOptional = accountRepository.findAccountByEmail(principal.getName());
        VarahamihirAuthenticationThreadLocal.INSTANCE.initialize(accountOptional.get());
        return new PreAuthenticatedAuthenticationToken(principal, principal.getToken(), authorityList);
      }
      else {

        throw new BadCredentialsException("Invalid token");
      }*/
    }
    catch(Exception ex) {
      throw new BadCredentialsException("Communication failure with OAuth Server", ex);
    }
  }
  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PreAuthenticatedAuthenticationToken.class);
  }
}
