package com.avasthi.varahamihir.oauth2client.security.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oauth")
public interface OAuthClient {
  @PostMapping(value = "/oauth2/token", consumes = "application/x-www-form-urlencoded")
  OAuthResponse checkToken(@RequestParam("token") String token,
                          @RequestParam("token_type") String tykenType);
/*  @PostMapping(value = "/oauth2/token", consumes = )
  OAuthResponse oauthPost(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("grant_type") String grantType,
                          @RequestParam("scope") String scope,
                          @RequestParam("client_id") String clientId,
                          @RequestParam("client_secret") String clientSecret);*/
}
