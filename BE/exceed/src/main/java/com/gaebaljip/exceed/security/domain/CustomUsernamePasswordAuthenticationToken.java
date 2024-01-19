package com.gaebaljip.exceed.security.domain;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final Long memberId;

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long memberId) {
        super(principal, credentials, authorities);
        this.memberId = memberId;
    }
}
