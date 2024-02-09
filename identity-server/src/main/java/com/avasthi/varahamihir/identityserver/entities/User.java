package com.avasthi.varahamihir.identityserver.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends AbstractBase implements UserDetails {
    private UUID tenantId;
    private String fullname;
    @Indexed(name = "user_username_index", unique = true)
    private String username;
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Indexed(name = "user_email_index", unique = true)
    private String email;
    private String profilePicture;
    private Set<Role> grantedAuthorities;
    private boolean expired = false;
    private boolean locked = false;
    private boolean credentialsExpired = false;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList());
    }

    public Collection<Role> getGrantedAuthorities() {
        return grantedAuthorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}