package com.avasthi.varahamihir.identityserver.permissions;

import com.avasthi.varahamihir.identityserver.entities.Role;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPermissions {
    private final UserService userService;

    public boolean authorizedForAdminAndCurrentUser(User principal, String username) {
        return principal.getUsername().equalsIgnoreCase(username);
    }
}
