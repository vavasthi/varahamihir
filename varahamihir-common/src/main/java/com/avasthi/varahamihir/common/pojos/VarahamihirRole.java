/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by vinay on 1/28/16.
 */
public class VarahamihirRole implements GrantedAuthority {
    private String authority;

    public VarahamihirRole(String authority) {
        this.authority = authority;
    }

    public VarahamihirRole() {
    }

    @Override
    public String toString() {
        return "VarahamihirRole{" +
                "authority='" + authority + '\'' +
                '}';
    }

    @Override
    @JsonGetter("role")
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VarahamihirRole varahamihirRole = (VarahamihirRole) o;

        return authority != null ? authority.equals(varahamihirRole.authority) : varahamihirRole.authority == null;

    }

    @Override
    public int hashCode() {
        return authority != null ? authority.hashCode() : 0;
    }
}
