package com.example.risingtest.base;

import lombok.Getter;

@Getter
public enum BaseRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    BaseRole(String role) {
        this.role = role;
    }
}
