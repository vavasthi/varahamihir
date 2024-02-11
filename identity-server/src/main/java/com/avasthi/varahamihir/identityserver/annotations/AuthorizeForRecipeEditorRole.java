package com.avasthi.varahamihir.identityserver.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT_ADMIN') or (hasAuthority('RECIPE_EDITOR'))")
public @interface AuthorizeForRecipeEditorRole {
}
