package com.avasthi.varahamihir.identityserver.annotations;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN') or (hasAuthority('USER') and @userPermissions.authorizedForAdminAndCurrentUser(principal, #username))")
public @interface AuthorizeForCurrentUser {
}
