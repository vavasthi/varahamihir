package com.avasthi.varahamihir.identityserver.mappers;

import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.common.pojos.VarahamihirUserDetails;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
  public Mono<UserDetails> convert(VarahamihirClientDetails c) {

    Set<GrantedAuthority> grantedAuthorities
            = new HashSet<>(Arrays.asList(new VarahamihirGrantedAuthority(Role.AUTHENTICATE.name())));
    return Mono.just(VarahamihirUserDetails.builder()
            .userName(c.getClientId())
            .password(c.getClientSecret())
            .tenantId(c.getTenantId())
            .grantedAuthorities(grantedAuthorities)
            .accountMonExpired(!c.isExpired())
            .accountNonLocked(!c.isLocked())
            .credentialsNonExpired(!c.isCredentialsLocked())
            .build()); // Not copying password into pojo
  }
}
