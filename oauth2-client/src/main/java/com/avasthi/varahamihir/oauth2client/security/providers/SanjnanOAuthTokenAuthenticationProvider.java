package com.avasthi.varahamihir.oauth2client.security.providers;


import com.google.gson.Gson;
import com.avasthi.varahamihir.common.couchbase.AccountRepository;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.H2ORole;
import com.avasthi.varahamihir.common.security.SanjnanAuthenticationThreadLocal;
import com.avasthi.varahamihir.common.security.token.SanjnanOAuthTokenPrincipal;
import okhttp3.*;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SanjnanOAuthTokenAuthenticationProvider implements AuthenticationProvider {

  private final static Logger logger = Logger.getLogger(SanjnanOAuthTokenAuthenticationProvider.class);

  private final String requestUrl = "http://localhost:8081/oauth/check_token?token=%s&token_type=bearer";
  private final String clientId = "android-client";
  private final String clientSecret = "android-secret";

  @Autowired
  private AccountRepository accountRepository;

  class OAuthResponse {
    List<String> aud = new ArrayList<>();
    String user_name;
    List<String> scope = new ArrayList<>();
    boolean active;
    DateTime expiry;
    List<String> authorities = new ArrayList<>();
    String client_id;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    SanjnanOAuthTokenPrincipal principal = (SanjnanOAuthTokenPrincipal)authentication.getPrincipal();
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

      Request request = new Request.Builder()
              .url(String.format(requestUrl, principal.getToken().get()))
              .post(RequestBody.create(MediaType.get("application/x-www-form-urlencoded"), ""))
              .build();
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        Gson gson = new Gson();
        OAuthResponse oAuthResponse = gson.fromJson(response.body().string(), OAuthResponse.class);
        principal.setName(oAuthResponse.user_name);
        List<H2ORole> authorityList = new ArrayList<>();
        if (oAuthResponse.authorities != null) {

          oAuthResponse.authorities.stream().forEach(e -> authorityList.add(new H2ORole(e)));
        }
        Optional<Account> accountOptional = accountRepository.findAccountByEmail(principal.getName());
        SanjnanAuthenticationThreadLocal.INSTANCE.initialize(accountOptional.get());
        return new PreAuthenticatedAuthenticationToken(principal, principal.getToken(), authorityList);
      }
      else {

        throw new BadCredentialsException("Invalid token");
      }
    }
    catch(IOException ex) {
      throw new BadCredentialsException("Communication failure with OAuth Server", ex);
    }
  }
  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PreAuthenticatedAuthenticationToken.class);
  }
}
