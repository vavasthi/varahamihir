package com.avasthi.varahamihir.common.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VarahamihirGrantedAuthority implements GrantedAuthority, Serializable {

  private String authority;
}
