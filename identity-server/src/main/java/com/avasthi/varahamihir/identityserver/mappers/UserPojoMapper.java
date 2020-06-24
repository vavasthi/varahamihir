package com.avasthi.varahamihir.identityserver.mappers;

import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirRequestContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPojoMapper {

  public Optional<UserPojo> convert(Optional<User> optionalUser) {

    if (optionalUser.isPresent()) {
      User u = optionalUser.get();
      return Optional.of(convert(u));
    }
    return Optional.empty();
  }
  public UserPojo convert(User u) {

    return new UserPojo(u.getId(),
            u.getCreatedBy(),
            u.getUpdatedBy(),
            u.getCreatedAt(),
            u.getUpdatedAt(),
            u.getTenant().getId(),
            u.getFullname(),
            u.getUsername(),
            null,
            u.getEmail(),
            u.getMask(),
            u.getAuthToken(),
            u.getExpiry(),
            u.getGrantedAuthorities());
  }
  public User convert(UserPojo userPojo) {

    return new User(VarahamihirRequestContext.currentTenant.get(),
            userPojo.getFullname(),
            userPojo.getUsername(),
            userPojo.getEmail(),
            userPojo.getMask(),
            userPojo.getAuthToken(),
            userPojo.getExpiry());
  }
}
