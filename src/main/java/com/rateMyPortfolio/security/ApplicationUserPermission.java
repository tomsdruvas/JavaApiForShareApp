package com.rateMyPortfolio.security;

public enum ApplicationUserPermission {

    PORTFOLIO_READ("portfolio:read"),
    PORTFOLIO_WRITE("portfolio:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
