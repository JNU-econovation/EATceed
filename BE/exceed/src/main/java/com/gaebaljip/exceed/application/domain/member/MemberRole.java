package com.gaebaljip.exceed.application.domain.member;

public enum MemberRole {
    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private final String role;

    MemberRole(String role) {
        this.role = role;
    }
}
