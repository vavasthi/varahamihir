package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.common.pojos.VarahamihirUserDetails;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.common.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Log4j2
public class VarahamihirUserDetailService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws NotFoundException {
    log.error(String.format("%s looking for user.", username));
    Optional<User> user = userService.retrieveUser(username);
    if (user.isPresent()) {
      VarahamihirUserDetails userDetails = new VarahamihirUserDetails();
      userDetails.setUserName(user.get().getUsername());
      userDetails.setPassword(user.get().getPassword());
      Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
      for (String authority : user.get().getGrantedAuthorities()) {
        authorities.add(new VarahamihirGrantedAuthority(authority));
      }
      userDetails.setGrantedAuthorities(authorities);
      return userDetails;
    }
    else {

      VarahamihirUserDetails userDetails = new VarahamihirUserDetails();
      userDetails.setUserName(username);
      userDetails.setPassword("NEWUSER");
      Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
      for (String authority : user.get().getGrantedAuthorities()) {
        authorities.add(new VarahamihirGrantedAuthority(Role.NEWUSER.name()));
      }
      userDetails.setGrantedAuthorities(authorities);
      return userDetails;
    }
  }
}
