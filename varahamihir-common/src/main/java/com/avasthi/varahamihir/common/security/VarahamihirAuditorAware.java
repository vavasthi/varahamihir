package com.avasthi.varahamihir.common.security;

import com.avasthi.varahamihir.common.security.token.VarahamihirPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Created by vinay on 1/28/16.
 */

public class VarahamihirAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return Optional.of(((VarahamihirPrincipal) authentication.getPrincipal()).getName());
    }
}