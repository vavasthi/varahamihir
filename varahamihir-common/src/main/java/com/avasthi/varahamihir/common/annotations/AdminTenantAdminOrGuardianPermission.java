package com.avasthi.varahamihir.common.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN', 'GUARDIAN')")
public @interface AdminTenantAdminOrGuardianPermission {
}
