package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.identityserver.mappers.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("varahamihirReactiveClientUserDetailService")
public class VarahamihirReactiveClientUserDetailService
        implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

  @Autowired
  private ClientService clientService;
  @Autowired
  private UserDetailsMapper userDetailsMapper;

  @Override
  public Mono<UserDetails> updatePassword(UserDetails userDetails, String password) {
    return clientService.updatePassword(userDetails.getUsername(), password).flatMap(user -> {

      return userDetailsMapper.convert(user);
    }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("User %s doesn't exist",
            userDetails.getUsername()))));
  }

  @Override
  public Mono<UserDetails> findByUsername(String username) {

    return clientService.findByClientId(username).flatMap(clientDetails -> {

      return userDetailsMapper.convert(clientDetails);
    }).switchIfEmpty(Mono.error(new UnauthorizedException(String.format("User %s doesn't exist", username))));
  }
}

