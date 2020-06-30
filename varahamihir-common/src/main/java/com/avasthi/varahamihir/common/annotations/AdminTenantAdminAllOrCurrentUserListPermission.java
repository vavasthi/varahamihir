package com.avasthi.varahamihir.common.annotations;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('admin', 'tenant_admin', 'user')")
@PostFilter("hasAnyAuthority('admin', 'tenant_admin') or (hasAuthority('user') and filterObject.username == authentication.name)")
public @interface AdminTenantAdminAllOrCurrentUserListPermission {
}
