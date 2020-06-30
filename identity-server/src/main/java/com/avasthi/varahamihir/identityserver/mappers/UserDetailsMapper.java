package com.avasthi.varahamihir.identityserver.mappers;

import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.common.pojos.VarahamihirUserDetails;
import com.avasthi.varahamihir.identityserver.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class UserDetailsMapper {

  public Mono<UserDetails> convert(User u) {

      return Mono.just(VarahamihirUserDetails.builder()
              .userName(u.getUsername())
              .password(u.getPassword())
              .tenantId(u.getTenantId())
              .grantedAuthorities(u.getGrantedAuthorities().stream().map(s -> new VarahamihirGrantedAuthority(s)).collect(Collectors.toSet()))
              .accountMonExpired(!u.isExpired())
              .accountNonLocked(!u.isLocked())
              .credentialsNonExpired(!u.isCredentialsLocked())
              .build()); // Not copying password into pojo
  }
}
