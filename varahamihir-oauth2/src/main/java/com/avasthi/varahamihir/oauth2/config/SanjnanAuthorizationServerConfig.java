package com.avasthi.varahamihir.oauth2.config;

import com.avasthi.varahamihir.oauth2.couchbase.CouchbaseAccessTokenRepository;
import com.avasthi.varahamihir.oauth2.couchbase.CouchbaseRefreshTokenRepository;
import com.avasthi.varahamihir.oauth2.services.VarahamihirClientDetailsService;
import com.avasthi.varahamihir.oauth2.services.VarahamihirTokenStore;
import com.avasthi.varahamihir.oauth2.services.VarahamihirUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class SanjnanAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private VarahamihirTokenStore tokenStore;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Autowired
  private VarahamihirClientDetailsService clientDetailsService;

  @Autowired
  private VarahamihirUserDetailService userDetailService;

  @Autowired
  private CouchbaseAccessTokenRepository couchbaseAccessTokenRepository;

  @Autowired
  private CouchbaseRefreshTokenRepository couchbaseRefreshTokenRepository;



  @Override
  public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

    configurer.withClientDetails(clientDetailsService);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore)
            .authenticationManager(authenticationManager).userDetailsService(userDetailService);
  }


  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
  }
  @Bean
  public TokenStore tokenStore() {
    return tokenStore;
  }
  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }
}