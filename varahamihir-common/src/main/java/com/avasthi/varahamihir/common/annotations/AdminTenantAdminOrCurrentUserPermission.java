package com.avasthi.varahamihir.common.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('admin', 'tenant_admin') or (hasAuthority('user') and #username == authentication.name)")
public @interface AdminTenantAdminOrCurrentUserPermission {
}
