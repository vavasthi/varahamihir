package com.avasthi.varahamihir.identityserver.pojo;

import com.avasthi.varahamihir.identityserver.entities.Role;

import java.util.Collection;
import java.util.List;

public record AuthToken(String username,
                        String fullname,
                        String authToken,
                        String refreshToken,
                        Collection<Role> authTokenRoles,
                        String profilePicture) {
}
