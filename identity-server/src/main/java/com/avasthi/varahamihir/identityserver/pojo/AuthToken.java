package com.avasthi.varahamihir.identityserver.pojo;

import com.avasthi.varahamihir.identityserver.entities.Role;

import java.util.Collection;
import java.util.List;

public record AuthToken(String username, String authToken, String refreshToken, Collection<Role> authTokenRoles) {
}
