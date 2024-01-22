package com.avasthi.varahamihir.identityserver.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

    USER("USER"),
    REFRESH("REFRESH"),
    ADMIN("ADMIN"),
    TENANT_ADMIN("TENANT_ADMIN");
    Role(String authority) {
        this.authority = new SimpleGrantedAuthority(authority);
    }
    public GrantedAuthority getAuthority() {
        return authority;
    }
    private SimpleGrantedAuthority authority;
}
