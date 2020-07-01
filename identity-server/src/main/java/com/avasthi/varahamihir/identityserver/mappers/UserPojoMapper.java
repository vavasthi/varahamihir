package com.avasthi.varahamihir.identityserver.mappers;

import com.avasthi.varahamihir.common.pojos.UserPojo;
import com.avasthi.varahamihir.identityserver.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserPojoMapper {

  public UserPojo convert(User u) {

      return UserPojo.builder()
              .id(u.getId())
              .createdAt(u.getCreatedAt())
              .createdBy(u.getCreatedBy())
              .updatedBy(u.getUpdatedBy())
              .username(u.getUsername())
              .updatedAt(u.getUpdatedAt())
              .tenantId(u.getTenantId())
              .fullname(u.getFullname())
              .email(u.getEmail())
              .grantedAuthorities(u.getGrantedAuthorities())
              .build(); // Not copying password into pojo
  }
  public User convert(UserPojo u) {

    return User.builder()
            .id(u.getId())
            .createdAt(u.getCreatedAt())
            .createdBy(u.getCreatedBy())
            .updatedBy(u.getUpdatedBy())
            .username(u.getUsername())
            .updatedAt(u.getUpdatedAt())
            .tenantId(u.getTenantId())
            .fullname(u.getFullname())
            .email(u.getEmail())
            .grantedAuthorities(u.getGrantedAuthorities())
            .password(u.getPassword())
            .build();
  }
}
