/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.oauth2client.security;

import com.avasthi.varahamihir.common.constants.SanjnanConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.oauth2client.security.filters.SanjnanAuthenticationFilter;
import com.avasthi.varahamihir.oauth2client.security.providers.SanjnanOAuthTokenAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by vinay on 1/27/16.
 */

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SanjnanOAuthClientSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(tokenAuthenticationProvider());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/manage/health");
    web.ignoring().antMatchers("/swagger-ui.html");
    web.ignoring().antMatchers("/webjars/**");
    web.ignoring().antMatchers("/v2/api-docs/**");
    web.ignoring().antMatchers("/swagger-resources/**");
    web.ignoring().antMatchers("/v1/fulfiller/signup");
    web.ignoring().antMatchers("/v1/customer/signup");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
        and().
        authorizeRequests().
            antMatchers(actuatorEndpoints()).hasRole(Role.ADMIN.getValue()).
        anyRequest().authenticated().
        and().
        anonymous().disable().
        exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

    http.addFilterBefore(new SanjnanAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
  }

  private String[] actuatorEndpoints() {
    return new String[]{
            SanjnanConstants.AUTOCONFIG_ENDPOINT,
            SanjnanConstants.BEANS_ENDPOINT,
            SanjnanConstants.CONFIGPROPS_ENDPOINT,
            SanjnanConstants.ENV_ENDPOINT,
            SanjnanConstants.MAPPINGS_ENDPOINT,
            SanjnanConstants.METRICS_ENDPOINT,
            SanjnanConstants.SHUTDOWN_ENDPOINT
    };
  }

  @Bean
  public AuthenticationEntryPoint unauthorizedEntryPoint() {
    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Bean
  public AuthenticationProvider tokenAuthenticationProvider() {
    return new SanjnanOAuthTokenAuthenticationProvider();
  }

}