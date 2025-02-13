package com.techdecode.blog.models.roles;

public enum UserRole {

    ADMIN("admin"),
    COMMON("common");

    private String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
