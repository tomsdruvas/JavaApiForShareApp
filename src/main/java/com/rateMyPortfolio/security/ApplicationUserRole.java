package com.rateMyPortfolio.security;

import static com.rateMyPortfolio.security.ApplicationUserPermission.PORTFOLIO_READ;
import static com.rateMyPortfolio.security.ApplicationUserPermission.PORTFOLIO_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(PORTFOLIO_READ, PORTFOLIO_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> userPermissions = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());

        userPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return userPermissions;
    }
}
